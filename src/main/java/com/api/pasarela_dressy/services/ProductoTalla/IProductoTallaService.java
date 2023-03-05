package com.api.pasarela_dressy.services.ProductoTalla;

import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;

public interface IProductoTallaService
{
    ProductoTallaDto create(CreateProductoTallaDto productoTallaDto);

    ProductoTallaDto update(UpdateProductoTallaDto productoTallaDto);
}
