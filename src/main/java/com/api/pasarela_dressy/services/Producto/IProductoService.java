package com.api.pasarela_dressy.services.Producto;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;

import java.util.List;

public interface IProductoService
{
    ProductoDto findById(String id_producto);
    List<ProductoDto> findAll();
    ProductoDto create(CreateProductoDto productoDto);
    ProductoDto update(UpdateProductoDto updateProductoDto, String id_producto);
    ProductoDto disable(String id_producto);
    ProductoDto enable(String id_producto);
    ProductoDto delete(String id_producto);

    ProductoDto restore(String id_producto);
}
