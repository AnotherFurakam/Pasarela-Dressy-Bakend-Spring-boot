package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.entity.ProductoTallaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoTallaMapper
{
    @Autowired
    ModelMapper mapper;

    public ProductoTallaDto toDto(ProductoTallaEntity productoTallaEntity)
    {
        ProductoTallaDto dto = mapper.map(productoTallaEntity, ProductoTallaDto.class);
        dto.getProducto().setMarca(productoTallaEntity.getProducto().getMarca().getNombre());
        dto.getProducto().setCategoria(productoTallaEntity.getProducto().getCategoria().getNombre());
        return dto;
    }

    public ProductoTallaEntity toEntity(CreateProductoTallaDto productoTallaDto)
    {
        return mapper.map(productoTallaDto, ProductoTallaEntity.class);
    }
}
