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

import java.util.UUID;

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
    public AsignacionDto getAll()
    {
        return null;
    }

    @Override
    public AsignacionDto getById()
    {
        return null;
    }

    @Override
    public AsignacionDto create(CreateAsignacionDto asignacion)
    {
        try {
            //Obteniendo información del empleado
            EmpleadoEntity empleado = empleadoRepository.findById(UUID.fromString(asignacion.getId_empleado()))
                .orElseThrow(() -> new NotFoundException("El empleado no fue encontrado"));

            //Obteniendo informacion del rol
            RolEntity rol = roleRepository.findById(UUID.fromString(asignacion.getId_rol()))
                .orElseThrow(() -> new NotFoundException("El rol no fue encontrado"));

            AsignacionEntity existAsignacion = asignacionRepository.getByEmpladoAndRol(empleado,rol);
            if(existAsignacion != null)
                throw new BadRequestException("Esta asignación ya existe, puede restaurarla si desea");

            //Comprobando que el empleado no este eliminado o desabilitado
            if (!empleado.getActivo() || empleado.getEliminado())
                throw new BadRequestException("El empleado fue eliminado o no esta activo");

            //Comprobando que el rol no este eliminado
            if (rol.getEliminado()) throw new BadRequestException("El rol fue eliminado");

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
        } catch (Exception e) {
            //Verificando si el error es un Not Found y lanzando el error
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());

            //De ser otro tipo de error lanzar un RuntimeException
            //esto se hace para que el controller advice lo detecte
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public AsignacionDto update(CreateAsignacionDto asignacion)
    {
        return null;
    }

    @Override
    public AsignacionDto delete(String id_asignacion)
    {
        return null;
    }

    @Override
    public AsignacionDto disable(String id_asignacion)
    {
        return null;
    }

    @Override
    public AsignacionDto enable(String id_asignacion)
    {
        return null;
    }

    @Override
    public AsignacionDto restore(String id_asignacion)
    {
        return null;
    }
}
