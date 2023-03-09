package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoMapper
{
    @Autowired
    ModelMapper mapper;

    public ProductoEntity toEntity(CreateProductoDto productoDto)
    {
        return mapper.map(productoDto, ProductoEntity.class);
    }

    public ProductoDto toDto(ProductoEntity productoEntity)
    {
        return mapper.map(productoEntity, ProductoDto.class);
    }

    public List<ProductoDto> toListDto(List<ProductoEntity> productoEntityList)
    {
        return productoEntityList.stream().map(p -> mapper.map(p, ProductoDto.class)).collect(Collectors.toList());
    }

    public void updateFromDto(UpdateProductoDto productoDto, ProductoEntity productoEntity)
    {
        mapper.map(productoDto,productoEntity);
    }
}
