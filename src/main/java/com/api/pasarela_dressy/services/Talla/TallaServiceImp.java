package com.api.pasarela_dressy.services.Talla;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.TallaRepository;
import com.api.pasarela_dressy.utils.mappers.TallaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TallaServiceImp implements ITallaService
{
    @Autowired
    TallaRepository tallaRepository;

    @Autowired
    TallaMapper tallaMapper;

    //* Utils methods

    /**
     * Obtiene la talla mediante su id
     * @param id_talla String del id de la talla
     * @return TallaEntity
     */
    public TallaEntity getTallaById(String id_talla)
    {
        try
        {
            return tallaRepository.findById(UUID.fromString(id_talla)).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Verifica si existe datos duplicados en la base de datos
     * @param nombre Nombre de la talla
     */
    private void throwErrorIfExistDuplicateData(String nombre)
    {
        List<String> errors = new ArrayList<>();
        TallaEntity tallaEntity = tallaRepository.getByName(nombre);
        if (tallaEntity != null)
        {
            if (!tallaEntity.getEliminado()) errors.add("Ya existe una talla con el nombre " + nombre);

            if (tallaEntity.getEliminado())
                errors.add("Ya existe una talla con el nombre " + nombre + " pero está eliminado");

            throw new UniqueFieldException(errors);
        }
    }

    /**
     * Verifica si existe datos duplicados en la base de datos <br>
     ** Excluye la entidad con el id pasado en el parámetro
     * @param nombre Nombre de la talla
     * @param id_talla Id de la talla a excluir de la verificación
     */
    private void throwErrorIfExistDuplicateDataWhenUpdate(
        String nombre,
        UUID id_talla
    )
    {
        List<String> errors = new ArrayList<>();
        TallaEntity tallaEntity = tallaRepository.getByNameButDifferentFromId(nombre, id_talla);
        if (tallaEntity != null)
        {
            if (!tallaEntity.getEliminado()) errors.add("Ya existe una talla con el nombre " + nombre);

            if (tallaEntity.getEliminado())
                errors.add("Ya existe una talla con el nombre " + nombre + " pero está eliminado");

            throw new UniqueFieldException(errors);
        }
    }


    //* Service methods
    @Override
    public List<TallaDto> getAll()
    {
        List<TallaEntity> tallas = tallaRepository.getAllUndeleted();
        return tallaMapper.toListDto(tallas);
    }

    @Override
    public TallaDto create(CreateTallaDto tallaDto)
    {
        this.throwErrorIfExistDuplicateData(tallaDto.getNombre());

        TallaEntity talla = tallaMapper.toEntity(tallaDto);

        return tallaMapper.toDto(tallaRepository.save(talla));
    }

    @Override
    public TallaDto update(
        UpdateTallaDto tallaDto,
        String id_talla
    )
    {
        TallaEntity talla = this.getTallaById(id_talla);

        this.throwErrorIfExistDuplicateDataWhenUpdate(tallaDto.getNombre(), talla.getId_talla());

        if (talla.getEliminado()) throw new BadRequestException("La talla fue eliminada");

        tallaMapper.updateFromDto(tallaDto, talla);

        return tallaMapper.toDto(tallaRepository.save(talla));
    }

    @Override
    public TallaDto delete(String id_talla)
    {
        TallaEntity talla = this.getTallaById(id_talla);

        if (talla.getEliminado()) throw new BadRequestException("La talla ya esta eliminada");

        talla.setEliminado(true);

        return tallaMapper.toDto(tallaRepository.save(talla));
    }

    @Override
    public TallaDto restore(String id_talla)
    {
        TallaEntity talla = this.getTallaById(id_talla);

        if (!talla.getEliminado()) throw new BadRequestException("La talla no esta eliminada");

        talla.setEliminado(false);

        return tallaMapper.toDto(tallaRepository.save(talla));
    }
}
