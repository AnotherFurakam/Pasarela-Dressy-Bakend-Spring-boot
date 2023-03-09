package com.api.pasarela_dressy.services.Empleado;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Empleado.ChangePasswordDto;
import com.api.pasarela_dressy.model.dto.Empleado.CreateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import com.api.pasarela_dressy.utils.mappers.EmpleadoMapper;
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
    EmpleadoMapper empleadoMapper;

    //* Utils Methods

    /**
     * Esta función encuentra un empleado mediante su id, en caso de no enconrtarlo lanza un error 404 Not Found
     *
     * @param id_empleado
     * @return El empleado encontrado mediante su id
     */
    public EmpleadoEntity getEmpleadoById(String id_empleado)
    {
        try
        {
            //* Al encontrar un error al convertir el texto en UUID será capturado y lanzado como un BadRequestException
            EmpleadoEntity empleado = empleado = empleadoRepository.findById(UUID.fromString(id_empleado)).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
            return empleado;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Este método comprueba si existe algún empleado ya registrado con el mismo dni y correo.<br>
     * * En caso de pasarle el parametro empleado se comprobará si el dni y correo ya existe en un usuario diferente al mismo
     *
     * @param dni_empleado
     * @param correo_empleado
     * @param empleado
     */
    private void existDuplicateData(
        String dni_empleado,
        String correo_empleado
    )
    {
        // * Creando lista que contendrá los errores de Unique constraint
        ArrayList<String> uniqueErrors = new ArrayList<>();

        EmpleadoEntity dni = empleadoRepository.getByDni(dni_empleado);
        if (dni != null) uniqueErrors.add("El dni: " + dni_empleado + ", ya existe.");

        EmpleadoEntity email = empleadoRepository.getByCorreo(correo_empleado);
        if (email != null) uniqueErrors.add("El correo: " + correo_empleado + ", ya existe");

        if (email != null || dni != null)
        {
            throw new UniqueFieldException(uniqueErrors);
        }
    }

    private void existDuplicateDataWhenUpdate(
        String dni,
        String correo,
        UUID id_empleado
    )
    {
        // *Creando lista que contendrá los errores de Unique constraint
        ArrayList<String> errors = new ArrayList<>();

        EmpleadoEntity existWithDni = empleadoRepository.getByDniAndId(dni, id_empleado);
        if (existWithDni != null) errors.add("Ya existe un empleado con el dni " + dni);

        EmpleadoEntity existWithCorreo = empleadoRepository.getByCorreoAndId(correo, id_empleado);
        if (existWithCorreo != null) errors.add("Ya existe un empleado con el correo " + correo);

        if (existWithDni != null || existWithCorreo != null)
        {
            throw new UniqueFieldException(errors);
        }
    }

    //* Services Methods

    @Override
    public List<EmpleadoDto> getAll()
    {
        List<EmpleadoEntity> empleados = empleadoRepository.getAllUndeleted();

        return empleadoMapper.toListDto(empleados);
    }

    @Override
    public EmpleadoDto getById(String id_empleado)
    {
        //* Obteniendo la información del empleado mediante su id
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        return empleadoMapper.toDto(empleadoEntity);
    }

    @Override
    public EmpleadoDto create(CreateEmpleadoDto empleadoDto)
    {
        //* Comprobando si ya existen empleado con el mismo dni y correo
        this.existDuplicateData(empleadoDto.getDni(), empleadoDto.getCorreo());

        EmpleadoEntity empleadoEntity = empleadoMapper.toEntity(empleadoDto);

        //*Asignando el número de dni como contaseña de forma predeterminada
        empleadoEntity.setContrasenia(empleadoDto.getDni());

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

    }

    @Override
    public EmpleadoDto update(
        UpdateEmpleadoDto empleadoDto,
        String id_empleado
    )
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

        // *Comprobando si los valores únicos son diferentes al que tiene actualmente y determinar su duplicidad con otras entidades para lanzar el error correspondiente
        this.existDuplicateDataWhenUpdate(empleadoDto.getDni(), empleadoDto.getCorreo(), empleadoEntity.getId_empleado());

        empleadoMapper.updateEmpleadoFromDto(empleadoDto, empleadoEntity);

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }

    @Override
    public EmpleadoDto delete(String id_empleado)
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);
        if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

        empleadoEntity.setEliminado(true);

        empleadoEntity.setActivo(false);

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }

    @Override
    public EmpleadoDto restore(String id_empleado)
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        if (!empleadoEntity.getEliminado()) throw new BadRequestException("El empleado no está eliminado");

        empleadoEntity.setEliminado(false);

        empleadoEntity.setActivo(true);

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }

    @Override
    public EmpleadoDto disable(String id_empleado)
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
        if (!empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está desabilitado");

        empleadoEntity.setActivo(false);

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }

    @Override
    public EmpleadoDto enable(String id_empleado)
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
        if (empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está activo");

        empleadoEntity.setActivo(true);

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }

    @Override
    public EmpleadoDto changePassword(
        String id_empleado,
        ChangePasswordDto passwordDto
    )
    {
        EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

        if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
        if (!empleadoEntity.getActivo()) throw new BadRequestException("El empleado no está activo");

        empleadoEntity.setContrasenia(passwordDto.getContrasenia());

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));
    }
}
