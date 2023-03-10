package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TallaMapper
{
    @Autowired
    ModelMapper mapper;

    public TallaDto toDto(TallaEntity tallaEntity)
    {
        return mapper.map(tallaEntity, TallaDto.class);
    }

    public TallaEntity toEntity(CreateTallaDto tallaDto)
    {
        return mapper.map(tallaDto, TallaEntity.class);
    }

    public List<TallaDto> toListDto(List<TallaEntity> tallaEntityList)
    {
        return tallaEntityList.stream().map(t -> mapper.map(t, TallaDto.class)).collect(Collectors.toList());
    }

    public void updateFromDto(UpdateTallaDto tallaDto, TallaEntity tallaEntity)
    {
        mapper.map(tallaDto, tallaEntity);
    }
}
