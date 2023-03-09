package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarcaMapper
{
    @Autowired
    ModelMapper mapper;

    public MarcaDto toDto(MarcaEntity marcaEntity)
    {
        return mapper.map(marcaEntity, MarcaDto.class);
    }

    public List<MarcaDto> toDtoList(List<MarcaEntity> marcaEntityList)
    {
        return marcaEntityList.stream().map(m -> mapper.map(m, MarcaDto.class)).collect(Collectors.toList());
    }

    public MarcaEntity toEntity(CreateMarcaDto marcaDto)
    {
        return mapper.map(marcaDto, MarcaEntity.class);
    }

    public void updateFromDto(UpdateMarcaDto marcaDto, MarcaEntity marcaEntity)
    {
        mapper.map(marcaDto, marcaEntity);
    }
}
