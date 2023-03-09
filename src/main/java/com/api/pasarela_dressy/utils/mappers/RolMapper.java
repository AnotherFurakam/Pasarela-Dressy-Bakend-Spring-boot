package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Rol.RolDto;
import com.api.pasarela_dressy.model.dto.Rol.UpdateRolDto;
import com.api.pasarela_dressy.model.entity.RolEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolMapper
{
    @Autowired
    ModelMapper mapper;

    public RolDto toDto(RolEntity rolEntity)
    {
        return mapper.map(rolEntity, RolDto.class);
    }

    public RolEntity toEntity(Object rolDto)
    {
        return mapper.map(rolDto, RolEntity.class);
    }

    public void updateEntityFromDto(UpdateRolDto rolDto, RolEntity rolEntity)
    {
        mapper.map(rolDto, rolEntity);
    }

    public List<RolDto> toListDto(List<RolEntity> rolEntityList)
    {
        return rolEntityList.stream().map(r -> mapper.map(r, RolDto.class)).collect(Collectors.toList());
    }
}
