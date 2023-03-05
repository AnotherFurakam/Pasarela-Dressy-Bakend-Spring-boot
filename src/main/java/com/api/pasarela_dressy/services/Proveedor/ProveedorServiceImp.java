package com.api.pasarela_dressy.services.Proveedor;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;
import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import com.api.pasarela_dressy.repository.ProveedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImp implements IProveedorService
{
    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ProveedorDto create(CreateProveedorDto proveedorDto)
    {
        try
        {
            //Verificando si el proveedor ya existe
            ProveedorEntity findByName = proveedorRepository.findByName(proveedorDto.getNombre());
            if (findByName != null && findByName.getEliminado())
                throw new BadRequestException("El proveedor con el nombre " + proveedorDto.getNombre() + " ya existe pero esta eliminado");
            if (findByName != null) throw new BadRequestException("El proveedor con el nombre " + proveedorDto.getNombre() + " ya existe");

            ProveedorEntity createProveedorEntity = mapper.map(proveedorDto, ProveedorEntity.class);

            return mapper.map(proveedorRepository.save(createProveedorEntity), ProveedorDto.class);

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<ProveedorDto> getAll()
    {
        try
        {
            List<ProveedorEntity> proveedores = proveedorRepository.findAllUndeleted();
            return proveedores.stream().map(p -> mapper.map(p, ProveedorDto.class)).collect(Collectors.toList());
        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProveedorDto update(UpdateProveedorDto proveedorDto, String id_proveedor)
    {
        try
        {
            ProveedorEntity proveedorToUpdate = proveedorRepository.findById(UUID.fromString(id_proveedor)).orElseThrow(() -> new NotFoundException("El proveedor no fue encontrado"));

            if (proveedorToUpdate.getEliminado())
                throw new BadRequestException("El proveedor fue eliminado");

            mapper.map(proveedorDto, proveedorToUpdate);

            return mapper.map(proveedorRepository.save(proveedorToUpdate), ProveedorDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProveedorDto delete(String id_proveedor)
    {
        try
        {
            ProveedorEntity proveedorToDelete = proveedorRepository.findById(UUID.fromString(id_proveedor)).orElseThrow(() -> new NotFoundException("El proveedor no fue encontrado"));

            if (proveedorToDelete.getEliminado())
                throw new BadRequestException("El proveedor ya fue eliminado");

            //Eliminando proveedor de forma lógica
            proveedorToDelete.setEliminado(true);

            return mapper.map(proveedorRepository.save(proveedorToDelete), ProveedorDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProveedorDto restore(String id_proveedor)
    {
        try
        {
            ProveedorEntity proveedorToRestore = proveedorRepository.findById(UUID.fromString(id_proveedor)).orElseThrow(() -> new NotFoundException("El proveedor no fue encontrado"));

            if (!proveedorToRestore.getEliminado())
                throw new BadRequestException("El proveedor no está eliminado");

            //Restaurando proveedor de forma lógica
            proveedorToRestore.setEliminado(false);

            return mapper.map(proveedorRepository.save(proveedorToRestore), ProveedorDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
