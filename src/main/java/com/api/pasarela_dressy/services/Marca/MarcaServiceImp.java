package com.api.pasarela_dressy.services.Marca;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.repository.MarcaRepository;
import com.api.pasarela_dressy.utils.Capitalize;
import com.api.pasarela_dressy.utils.mappers.MarcaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImp implements IMarcaService
{

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    Capitalize capitalize;

    @Autowired
    MarcaMapper marcaMapper;

    //* Utils methdos

    /**
     * Obtiene los datos de la marca mediante su id
     *
     * @param id_marca Id de la marca como String
     * @return MarcaEntity con los datos correspondientes
     */
    public MarcaEntity getMarcaById(String id_marca)
    {
        try
        {
            return marcaRepository.findById(UUID.fromString(id_marca)).orElseThrow(() -> new NotFoundException("Marca no encontrada"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Verifica si la marca con el nombre indicado ya existe en la base de datos
     * * Toma en cuenta todos los registros sin excepción
     *
     * @param nombre
     */
    private void existDuplicateData(String nombre)
    {
        List<String> errors = new ArrayList<>();
        MarcaEntity findedByName = marcaRepository.getByNombre(nombre);
        if (findedByName != null)
        {
            errors.add("Ya existe la marca con el nombre " + nombre);
            throw new UniqueFieldException(errors);
        }
    }

    /**
     * Verifica si la marca con el nombre indicado ya existe en la base de dotos
     ** Toma en cuenta todos los registros menos el que tenga el id de marca indicado
     * @param nombre nombre de la marca a comprobar
     * @param id_marca id de la marca a excluir del filtrado
     */
    private void existDuplicateDataWhenUpdate(
        String nombre,
        UUID id_marca
    )
    {
        List<String> errors = new ArrayList<>();
        MarcaEntity findedByNameAndId = marcaRepository.getByNombreAndId(nombre, id_marca);
        if (findedByNameAndId != null)
        {
            errors.add("Ya existe la marca con el nombre " + nombre);
            throw new UniqueFieldException(errors);
        }
    }


    //* Service methods
    @Override
    public List<MarcaDto> getAll()
    {
        List<MarcaEntity> marcasFinded = marcaRepository.getAllUndeleted();

        return marcaMapper.toDtoList(marcasFinded);
    }

    @Override
    public MarcaDto getById(String id_marca)
    {
        MarcaEntity categoria = this.getMarcaById(id_marca);
        return marcaMapper.toDto(categoria);
    }

    @Override
    public MarcaDto create(CreateMarcaDto marcaDto)
    {
        String capitalizedName = capitalize.CapitalizeText(marcaDto.getNombre());
        marcaDto.setNombre(capitalizedName);

        this.existDuplicateData(capitalizedName);

        MarcaEntity marca = marcaMapper.toEntity(marcaDto);

        return marcaMapper.toDto(marcaRepository.save(marca));
    }

    @Override
    public MarcaDto update(
        UpdateMarcaDto marcaDto,
        String id_marca
    )
    {
        String capitalizedName = capitalize.CapitalizeText(marcaDto.getNombre());
        marcaDto.setNombre(capitalizedName);

        MarcaEntity marca = this.getMarcaById(id_marca);

        if (marca.getEliminado()) throw new BadRequestException("La marca " + marca.getNombre() + " esta eliminada");

        this.existDuplicateDataWhenUpdate(capitalizedName, marca.getId_marca());

        marcaMapper.updateFromDto(marcaDto, marca);

        return marcaMapper.toDto(marcaRepository.save(marca));
    }

    @Override
    public MarcaDto delete(String id_marca)
    {
        MarcaEntity marca = this.getMarcaById(id_marca);

        if (marca.getEliminado()) throw new BadRequestException("La marca " + marca.getNombre() + " esta eliminada");

        marca.setEliminado(true);

        return marcaMapper.toDto(marcaRepository.save(marca));
    }

    @Override
    public MarcaDto restore(String id_marca)
    {
        MarcaEntity marca = this.getMarcaById(id_marca);

        if (!marca.getEliminado())
            throw new BadRequestException("La marca " + marca.getNombre() + " no esta eliminada");

        marca.setEliminado(false);

        return marcaMapper.toDto(marcaRepository.save(marca));
    }
}
