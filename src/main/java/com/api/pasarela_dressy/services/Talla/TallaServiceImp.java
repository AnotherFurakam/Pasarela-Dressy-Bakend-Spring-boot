package com.api.pasarela_dressy.services.Talla;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.TallaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TallaServiceImp implements ITallaService
{
    @Autowired
    TallaRepository tallaRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<TallaDto> getAll()
    {
        List<TallaEntity> tallas = tallaRepository.getAllUndeleted();
        return tallas.stream().map(t -> mapper.map(t, TallaDto.class)).collect(Collectors.toList());
    }

    @Override
    public TallaDto create(CreateTallaDto tallaDto)
    {
        try
        {
            //Verificando si la talla con el mismo nombre existe
            TallaEntity tallaFind = tallaRepository.getByName(tallaDto.getNombre());

            //Si existe y esta eliminado se procede a restaurar y luego a retornar
            if (tallaFind != null && tallaFind.getEliminado())
                throw new BadRequestException("La talla con el nombre" + tallaFind.getNombre() + "ya existe pero esta eliminado");
            if (tallaFind != null) throw new BadRequestException("La talla con el nombre " + tallaFind.getNombre() + " ya existe");

            TallaEntity talla = mapper.map(tallaDto, TallaEntity.class);

            return mapper.map(tallaRepository.save(talla), TallaDto.class);

        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public TallaDto update(UpdateTallaDto tallaDto, String id_talla)
    {
        try
        {
            TallaEntity talla = tallaRepository.findById(UUID.fromString(id_talla)).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            if (talla.getEliminado()) throw new BadRequestException("La talla fue eliminada, para modificar debe restaurarlo");

            mapper.map(tallaDto, talla);

            return mapper.map(tallaRepository.save(talla), TallaDto.class);
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public TallaDto delete(String id_talla)
    {
        try
        {
            TallaEntity talla = tallaRepository.findById(UUID.fromString(id_talla)).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            if (talla.getEliminado()) throw new BadRequestException("La talla ya esta eliminada");

            talla.setEliminado(true);

            return mapper.map(tallaRepository.save(talla), TallaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public TallaDto restore(String id_talla)
    {
        try
        {
            TallaEntity talla = tallaRepository.findById(UUID.fromString(id_talla)).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            if (!talla.getEliminado()) throw new BadRequestException("La talla no esta eliminada");

            talla.setEliminado(false);

            return mapper.map(tallaRepository.save(talla), TallaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
