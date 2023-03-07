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

    //? Private Methods

    /**
     * Esta función encuentra un empleado mediante su id, en caso de no enconrtarlo lanza un error 404 Not Found
     *
     * @param id_empleado
     * @return El empleado encontrado mediante su id
     */
    private EmpleadoEntity getEmpleadoById(String id_empleado)
    {
        EmpleadoEntity empleado = null;
        try
        {
            empleado = empleadoRepository.findById(UUID.fromString(id_empleado)).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        return empleado;
    }

    /**
     * Comprueba si el dni o correo se repite en la tabla de empleados.
     * Cuando vayamos a crear un empleado solo le pasamos el dni, correo y empleado en null para que
     * este método verifique que no se repita los datos en la tabla.<br>
     * Cuando se va a actualizar debemos dar como parametro empleado la entidad a actualizar
     * con el fin de que el método haga la comparación respectiva y detecte si el dni o email se repite
     * en otras entidades excepto en la misma y lance los errores correspondientes.
     * En caso de no encontrarse repeticiones simplemento no devuelve errores.
     *
     * @param dni_empleado
     * @param correo_empleado
     * @param empleado
     */
    private void existDuplicateData(
        String dni_empleado,
        String correo_empleado,
        EmpleadoEntity empleado
    )
    {
        //Creando lista que contendrá los errores de Unique constraint
        ArrayList<String> uniqueErrors = new ArrayList<>();

        //Verificando si el dni ya existe
        EmpleadoEntity dni = empleadoRepository.getByDni(dni_empleado);

        //En caso de existir se añade a la lista de errores
        if (dni != null) uniqueErrors.add("El dni: " + dni_empleado + ", ya existe.");

        //Verificando si el email ya existe
        EmpleadoEntity email = empleadoRepository.getByCorreo(correo_empleado);

        //En caso de existir se añade a la lista de errores
        if (email != null) uniqueErrors.add("El correo: " + correo_empleado + ", ya existe");

        //Comprobando si el empleado es diferente de nulo
        if (empleado != null)
        {
            //Comprobando si existe un empleado con el mismo email
            if (email != null)
            {
                //Si es diferente del email del empleado a editar entonces lanza el error
                if (!empleado.getCorreo().equals(email.getCorreo()))
                {
                    throw new UniqueFieldException("Datos ya existentes", uniqueErrors);
                }
            }
            //Comprobando si existe un empleado con el mismo dni
            if (dni != null)
            {
                //Si se diferente del dni del empleado a editar entonces laza el error
                if (!empleado.getDni().equals(dni.getDni()))
                {
                    throw new UniqueFieldException("Datos ya existentes", uniqueErrors);
                }
            }
        } else
        {
            //Comprobando que alguno de los datos ya existan
            if (email != null || dni != null)
            {
                //Lanzando error con los eroers correspondientes
                throw new UniqueFieldException("Datos ya existentes", uniqueErrors);
            }
        }
    }

    //? Services Methods

    @Override
    public List<EmpleadoDto> getAll()
    {
        List<EmpleadoEntity> empleados = empleadoRepository.getAllUndeleted();

        return empleadoMapper.toListDto(empleados);
    }

    @Override
    public EmpleadoDto getById(String id_empleado)
    {
        try
        {
            EmpleadoEntity empleadoEntity = getEmpleadoById(id_empleado);
            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            return empleadoMapper.toDto(empleadoEntity);

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto create(CreateEmpleadoDto empleado)
    {
        //Comprobando si existen datos duplicados
        this.existDuplicateData(empleado.getDni(), empleado.getCorreo(), null);

        //Mapeando datos del dto a la entidad
        EmpleadoEntity empleadoEntity = empleadoMapper.toEntity(empleado);

        //Asignando el número de dni como contaseña automaticamente
        //(Esto podrá ser cambiado por el usuario mediante el endpoint correspondiente)
        empleadoEntity.setContrasenia(empleado.getDni());

        return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

    }

    @Override
    public EmpleadoDto update(
        UpdateEmpleadoDto empleado,
        String id_empleado
    )
    {
        try
        {
            //Obteniendo el empleado mediante su id
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

            //Comprobando si esta eliminado y lanzando el error correspondiente
            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            //Comprobando si los valores únicos son diferentes al que tiene actualmente y determinar su duplicidad para lanzar el error correspondiente
            this.existDuplicateData(empleado.getDni(), empleado.getCorreo(), empleadoEntity);

            //Mapeando los datos del dto a la entidad
            empleadoMapper.updateEntity(empleado, empleadoEntity);

            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto delete(String id_empleado)
    {
        try
        {
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);
            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            //Eliminando empleado de forma lógica
            empleadoEntity.setEliminado(true);

            //Desabilitando empleado automaticamente
            empleadoEntity.setActivo(false);


            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto restore(String id_empleado)
    {
        try
        {
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

            //Comprobando si el empleado ya fue eliminado
            if (!empleadoEntity.getEliminado()) throw new BadRequestException("El empleado no está eliminado");

            //Eliminando empleado de forma lógica
            empleadoEntity.setEliminado(false);

            //Desabilitando empleado automaticamente
            empleadoEntity.setActivo(true);

            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto disable(String id_empleado)
    {
        try
        {
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
            if (!empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está desabilitado");

            empleadoEntity.setActivo(false);

            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto enable(String id_empleado)
    {
        try
        {
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");
            if (empleadoEntity.getActivo()) throw new BadRequestException("El empleado ya está activo");

            empleadoEntity.setActivo(true);

            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EmpleadoDto changePassword(
        String id_empleado,
        ChangePasswordDto passwordDto
    )
    {
        try
        {
            //Buscando empleado mediante su uuid
            EmpleadoEntity empleadoEntity = this.getEmpleadoById(id_empleado);

            //Verificando si el no está empleado esta eliminado
            if (empleadoEntity.getEliminado()) throw new BadRequestException("El empleado está eliminado");

            //Verificando si el empleado esta cativo
            if (!empleadoEntity.getActivo()) throw new BadRequestException("El empleado no está activo");

            empleadoEntity.setContrasenia(passwordDto.getContrasenia());

            //Retornando el mapeado de la entidad del empleado que se guardó en la base de datos
            return empleadoMapper.toDto(empleadoRepository.save(empleadoEntity));

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }
}
