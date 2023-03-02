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
    AsignacionRepository asignacionRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<AsignacionDto> getAll()
    {
        List<AsignacionEntity> asignacionEntityList = asignacionRepository.getAllUndelete();

        return asignacionEntityList.stream().map(a -> mapper.map(a, AsignacionDto.class)).collect(Collectors.toList());
    }

    @Override
    public AsignacionDto getById(String id_asignacion)
    {
        try
        {
            //Obteniendo información del empleado
            AsignacionEntity asignacionEntity = asignacionRepository.findById(UUID.fromString(id_asignacion)).orElseThrow(() -> new NotFoundException("El empleado no fue encontrado"));

            //Comprobando si la asignación esta eliminada
            if (asignacionEntity.getEliminado())
                throw new BadRequestException("La asignación fue eliminada, para restaurarla solo debe asignarlo nuevamente");

            return mapper.map(asignacionEntity, AsignacionDto.class);
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public AsignacionDto create(CreateAsignacionDto asignacion)
    {
        try
        {
            //Obteniendo información del empleado
            EmpleadoEntity empleado = empleadoRepository.findById(UUID.fromString(asignacion.getId_empleado())).orElseThrow(() -> new NotFoundException("El empleado no fue encontrado"));

            //Comprobando que el empleado no este eliminado o desabilitado
            if (!empleado.getActivo() || empleado.getEliminado())
                throw new BadRequestException("El empleado fue eliminado o no esta activo");

            //Obteniendo informacion del rol
            RolEntity rol = roleRepository.findById(UUID.fromString(asignacion.getId_rol())).orElseThrow(() -> new NotFoundException("El rol no fue encontrado"));

            //Comprobando que el rol no este eliminado
            if (rol.getEliminado()) throw new BadRequestException("El rol fue eliminado");

            AsignacionEntity existAsignacion = asignacionRepository.getByEmpladoAndRol(empleado, rol);
            if (existAsignacion != null)
            {
                //Restaurando asignación ya creada
                if (existAsignacion.getEliminado())
                {
                    existAsignacion.setEliminado(false);
                    return mapper.map(asignacionRepository.save(existAsignacion), AsignacionDto.class);
                } else
                {
                    return mapper.map(existAsignacion, AsignacionDto.class);
                }

            }

            //En caso de no existir una asignación con el mismo empleado y rol se procederá con el registro

            //Creando la entdidad de asignación
            AsignacionEntity asignacionEntity = new AsignacionEntity();

            //Agregando el empleado y rol
            asignacionEntity.setEmpleado(empleado);
            asignacionEntity.setRol(rol);

            //Guardando en la bd y mapeando la asignación en el dto de respuesta
            AsignacionDto asignacionDto = mapper.map(asignacionRepository.save(asignacionEntity), AsignacionDto.class);

            //Asignando los datos del empleado al dto de respuesta
            asignacionDto.setEmpleado(mapper.map(empleado, ShortEmpleadoDto.class));

            //Asignando los datos del rol al dto de respuesta
            asignacionDto.setRol(mapper.map(rol, ShortRolDto.class));

            //Retornando el dto de respuesta
            return asignacionDto;
        } catch (Exception e)
        {
            //Verificando si el error es un Not Found y lanzando el error
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            //De ser otro tipo de error lanzar un RuntimeException
            //esto se hace para que el controller advice lo detecte
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public AsignacionDto delete(String id_asignacion)
    {
        try
        {
            AsignacionEntity asignacionEntity = asignacionRepository.findById(UUID.fromString(id_asignacion)).orElseThrow(() -> new NotFoundException("La asignación no fue encontrada"));

            if (asignacionEntity.getEliminado()) throw new BadRequestException("La asignación ya fue eliminada");

            //Eliminanado asignación de forma lógica
            asignacionEntity.setEliminado(true);

            return mapper.map(asignacionRepository.save(asignacionEntity), AsignacionDto.class);
        } catch (Exception e)
        {

            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            throw new BadRequestException(e.getMessage());
        }
    }

}
