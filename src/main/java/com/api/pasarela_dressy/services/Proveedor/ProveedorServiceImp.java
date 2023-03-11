package com.api.pasarela_dressy.services.Proveedor;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;
import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import com.api.pasarela_dressy.repository.ProveedorRepository;
import com.api.pasarela_dressy.utils.mappers.ProveedorMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImp implements IProveedorService
{
    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ProveedorMapper proveedorMapper;

    //* Utils methods

    /**
     * Obtiene el proveedor mediante su id. <br>
     * * En caso de no encontrarlo lanza un error de tipo NotFoundException
     *
     * @param id_proveedor Id proveedor como String
     * @return La entidad del proveedor encontrada
     */
    public ProveedorEntity getProveedorById(String id_proveedor)
    {
        try
        {
            return proveedorRepository.findById(UUID.fromString(id_proveedor)).orElseThrow(() -> new NotFoundException("El proveedor no fue encontrado"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Verifica si existe un proveedor con el nombre repetido<br>
     * * En caso de encontrar un proveedor con el nombre repetido se lanzara un error de tipo UniqueFieldException
     *
     * @param nombre String del nombre del proveedor a verificar
     */
    private void throwErrorIfExistDuplicateData(String nombre)
    {
        List<String> errors = new ArrayList<>();
        ProveedorEntity findByName = proveedorRepository.findByName(nombre);
        if (findByName != null && findByName.getEliminado())
        {
            errors.add("Ya existe el proveedor con el nombre " + findByName.getNombre() + " pero esta eliminado.");
            throw new UniqueFieldException(errors);
        }

        if (findByName != null)
        {
            errors.add("Ya existe el proveedor con el nombre " + findByName.getNombre());
            throw new UniqueFieldException(errors);
        }
    }

    /**
     * Verifica si el nombre de proveedor ya existe pero excluye a la entidad con el id igual al ingresado
     *
     * @param nombre       String del nombre a verificar
     * @param id_proveedor Id del proveedor a excluir
     */
    private void thorwErrorIfExistDuplicateDataWhenUpdate(
        String nombre,
        UUID id_proveedor
    )
    {
        try
        {
            List<String> errors = new ArrayList<>();
            ProveedorEntity findByName = proveedorRepository.findByNameButExcludeById(nombre, id_proveedor);
            if (findByName != null && findByName.getEliminado())
            {
                errors.add("Ya existe el proveedor con el nombre " + findByName.getNombre() + " pero esta eliminado.");
                throw new UniqueFieldException(errors);
            }

            if (findByName != null)
            {
                errors.add("Ya existe el proveedor con el nombre " + findByName.getNombre());
                throw new UniqueFieldException(errors);
            }
        } catch (RuntimeException e)
        {
            if (e instanceof UniqueFieldException)
                throw new UniqueFieldException(((UniqueFieldException) e).getErrors());
            throw new BadRequestException(e.getMessage());
        }
    }


    //* Service Methods
    @Override
    public ProveedorDto create(CreateProveedorDto proveedorDto)
    {
        this.throwErrorIfExistDuplicateData(proveedorDto.getNombre());

        ProveedorEntity createProveedorEntity = proveedorMapper.toEntity(proveedorDto);

        return proveedorMapper.toDto(proveedorRepository.save(createProveedorEntity));
    }

    @Override
    public List<ProveedorDto> getAll()
    {
        List<ProveedorEntity> proveedores = proveedorRepository.findAllUndeleted();
        return proveedorMapper.toListDto(proveedores);
    }

    @Override
    public ProveedorDto update(
        UpdateProveedorDto proveedorDto,
        String id_proveedor
    )
    {
        ProveedorEntity proveedorToUpdate = this.getProveedorById(id_proveedor);

        this.thorwErrorIfExistDuplicateDataWhenUpdate(proveedorDto.getNombre(), proveedorToUpdate.getId_proveedor());

        proveedorMapper.updateFromDto(proveedorDto, proveedorToUpdate);

        return proveedorMapper.toDto(proveedorRepository.save(proveedorToUpdate));
    }

    @Override
    public ProveedorDto delete(String id_proveedor)
    {
        ProveedorEntity proveedorToDelete = this.getProveedorById(id_proveedor);

        if (proveedorToDelete.getEliminado()) throw new BadRequestException("El proveedor ya fue eliminado");

        proveedorToDelete.setEliminado(true);

        return proveedorMapper.toDto(proveedorRepository.save(proveedorToDelete));
    }

    @Override
    public ProveedorDto restore(String id_proveedor)
    {
        ProveedorEntity proveedorToRestore = this.getProveedorById(id_proveedor);

        if (!proveedorToRestore.getEliminado()) throw new BadRequestException("El proveedor no está eliminado");

        proveedorToRestore.setEliminado(false);

        return proveedorMapper.toDto(proveedorRepository.save(proveedorToRestore));
    }
}
