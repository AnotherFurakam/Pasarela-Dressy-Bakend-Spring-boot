package com.api.pasarela_dressy.services.Asignacion;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Empleado.ShortEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Rol.ShortRolDto;
import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.CreateAsignacionDto;
import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.model.entity.RolEntity;
import com.api.pasarela_dressy.repository.AsignacionRepository;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import com.api.pasarela_dressy.repository.RoleRepository;
import com.api.pasarela_dressy.services.Empleado.EmpleadoService;
import com.api.pasarela_dressy.services.Empleado.EmpleadoServiceImp;
import com.api.pasarela_dressy.services.Role.RoleServiceImp;
import com.api.pasarela_dressy.utils.mappers.AsignacionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService
{

    @Autowired
    ModelMapper mapper;

    @Autowired
    AsignacionMapper asignacionMapper;

    @Autowired
    AsignacionRepository asignacionRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmpleadoServiceImp empleadoServiceImp;

    @Autowired
    RoleServiceImp roleServiceImp;

    //* Service private methods

    /**
     * Busca la asignación mediante su id en la base de datos
     * @param id_asignacion string del id de la asignación a buscar
     * @return la entidad de la asignación encontrada
     */
    private AsignacionEntity getAsigacionById(String id_asignacion)
    {
        try
        {
            AsignacionEntity asignacionEntity = asignacionRepository.findById(UUID.fromString(id_asignacion)).orElseThrow(() -> new NotFoundException("El empleado no fue encontrado"));

            return asignacionEntity;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    // * Service methods
    @Override
    public List<AsignacionDto> getAll()
    {
        List<AsignacionEntity> asignacionEntityList = asignacionRepository.getAllUndelete();

        return asignacionMapper.toListDto(asignacionEntityList);
    }

    @Override
    public AsignacionDto getById(String id_asignacion)
    {
        AsignacionEntity asignacionEntity = this.getAsigacionById(id_asignacion);
        return asignacionMapper.toDto(asignacionEntity);
    }

    @Override
    public AsignacionDto create(CreateAsignacionDto asignacionDto)
    {
        EmpleadoEntity empleado = empleadoServiceImp.getEmpleadoById(asignacionDto.getId_empleado());

        if (!empleado.getActivo() || empleado.getEliminado())
            throw new BadRequestException("El empleado fue eliminado o no esta activo");

        RolEntity rol = roleServiceImp.getRolById(asignacionDto.getId_rol());

        if (rol.getEliminado()) throw new BadRequestException("El rol fue eliminado");

        AsignacionEntity existAsignacion = asignacionRepository.getByEmpladoAndRol(empleado, rol);
        if (existAsignacion != null)
        {
            //* En caso de que la asignacion existente esté eliminada se restaurará y guardará los campbios de forma automático. Luego se se retornará
            if (existAsignacion.getEliminado())
            {
                existAsignacion.setEliminado(false);
                return mapper.map(asignacionRepository.save(existAsignacion), AsignacionDto.class);
            } else //* Si no está eliminada se devolverá un BadRequest indicando que ya existe
            {
                throw new BadRequestException("La asignación ya fue creada");
            }
        }

        //* En caso de no existir una asignación con el mismo empleado y rol se procederá con el registro

        AsignacionEntity asignacionEntity = new AsignacionEntity();
        asignacionEntity.setEmpleado(empleado);
        asignacionEntity.setRol(rol);

        return asignacionMapper.toDto(asignacionRepository.save(asignacionEntity));
    }

    @Override
    public AsignacionDto delete(String id_asignacion)
    {
        AsignacionEntity asignacionEntity = this.getAsigacionById(id_asignacion);

        if (asignacionEntity.getEliminado()) throw new BadRequestException("La asignación ya fue eliminada");

        //Eliminanado asignación de forma lógica
        asignacionEntity.setEliminado(true);

        return asignacionMapper.toDto(asignacionRepository.save(asignacionEntity));
    }

}
