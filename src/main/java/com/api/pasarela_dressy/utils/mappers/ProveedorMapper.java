package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;
import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorMapper
{
    @Autowired
    ModelMapper mapper;

    public ProveedorDto toDto(ProveedorEntity proveedorEntity)
    {
        return mapper.map(proveedorEntity, ProveedorDto.class);
    }

    public ProveedorEntity toEntity(CreateProveedorDto proveedorDto)
    {
        return mapper.map(proveedorDto, ProveedorEntity.class);
    }

    public void updateFromDto(UpdateProveedorDto proveedorDto, ProveedorEntity proveedorEntity)
    {
        mapper.map(proveedorDto, proveedorEntity);
    }

    public List<ProveedorDto> toListDto(List<ProveedorEntity> proveedorEntityList)
    {
        return proveedorEntityList.stream().map(p ->mapper.map(p, ProveedorDto.class)).collect(Collectors.toList());
    }
}
