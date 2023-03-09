package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AsignacionMapper
{
    @Autowired
    ModelMapper mapper;

    public AsignacionDto toDto(AsignacionEntity asignacionEntity)
    {
        return mapper.map(asignacionEntity, AsignacionDto.class);
    }

    public List<AsignacionDto> toListDto(List<AsignacionEntity> asignacionEntityList)
    {
        return asignacionEntityList.stream().map(a -> mapper.map(a, AsignacionDto.class)).collect(Collectors.toList());
    }
}
