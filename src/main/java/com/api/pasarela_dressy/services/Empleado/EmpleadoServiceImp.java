package com.api.pasarela_dressy.services.Empleado;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Empleado.CreateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmpleadoServiceImp implements EmpleadoService
{

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<EmpleadoDto> getAll()
    {
        List<EmpleadoEntity> empleados = empleadoRepository.getAllUndeleted();

        return empleados.stream().map(e -> mapper.map(e, EmpleadoDto.class)).toList();
    }

    @Override
    public EmpleadoDto getById(String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository.findById(UUID.fromString(id_empleado)).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            return mapper.map(empleadoEntity, EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto create(CreateEmpleadoDto empleado)
    {
        //Creando lista que contendrá los errores de Unique constraint
        ArrayList<String> uniqueErrors = new ArrayList<>();

        //Verificando si el dni ya existe
        EmpleadoEntity dni = empleadoRepository.getByDni(empleado.getDni());

        //En caso de existir se añade a la lista de errores
        if (dni != null) uniqueErrors.add("El dni: " + empleado.getDni() + ", ya existe.");

        //Verificando si el email ya existe
        EmpleadoEntity email = empleadoRepository.getByCorreo(empleado.getCorreo());

        //En caso de existir se añade a la lista de errores
        if (email != null) uniqueErrors.add("El correo: " + empleado.getCorreo() + ", ya existe");

        //Comprobando que alguno de los datos ya existan
        if (email != null || dni != null) {
            //Lanzando error con los eroers correspondientes
            throw new UniqueFieldException("Datos ya existentes", uniqueErrors);
        }

        //Mapeando datos del dto a la entidad
        EmpleadoEntity empleadoEntity = mapper.map(empleado, EmpleadoEntity.class);

        //Asignando el número de dni como contaseña automaticamente
        //(Esto podrá ser cambiado por el usuario mediante el endpoint correspondiente)
        empleadoEntity.setContrasenia(empleado.getDni());

        return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

    }

    @Override
    public EmpleadoDto update(UpdateEmpleadoDto empleado, String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository.findById(UUID.fromString(id_empleado)).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            mapper.map(empleado, empleadoEntity);

            return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto delete(String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository
                .findById(UUID.fromString(id_empleado))
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            //Comprobando si el empleado ya fue eliminado
            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado fue eliminado");

            //Eliminando empleado de forma lógica
            empleadoEntity.setEliminado(true);

            //Desabilitando empleado automaticamente
            empleadoEntity.setActivo(false);


            return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto restore(String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository
                .findById(UUID.fromString(id_empleado))
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            //Comprobando si el empleado ya fue eliminado
            if (!empleadoEntity.getEliminado()) throw new BadRequestException("El empleado no está eliminado");

            //Eliminando empleado de forma lógica
            empleadoEntity.setEliminado(false);

            //Desabilitando empleado automaticamente
            empleadoEntity.setActivo(true);

            return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto disable(String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository
                .findById(UUID.fromString(id_empleado))
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
            if (!empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está desabilitado");

            empleadoEntity.setActivo(false);

            return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto enable(String id_empleado)
    {
        try {
            EmpleadoEntity empleadoEntity = empleadoRepository.findById(UUID.fromString(id_empleado)).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
            if (empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está activo");

            empleadoEntity.setActivo(true);

            return mapper.map(empleadoRepository.save(empleadoEntity), EmpleadoDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
