package com.api.pasarela_dressy.services.Producto;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;

import java.util.List;

public interface IProductoService
{
    ProductoDto create(CreateProductoDto productoDto);
    List<ProductoDto> findAll();
}
