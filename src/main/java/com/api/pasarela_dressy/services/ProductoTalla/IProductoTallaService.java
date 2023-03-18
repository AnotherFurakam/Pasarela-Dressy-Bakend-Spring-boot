package com.api.pasarela_dressy.services.ProductoTalla;

import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;

public interface IProductoTallaService
{
    ProductoTallaDto create(DetalleEntradaEntity detalleEntradaEntity);

    ProductoTallaDto update(UpdateProductoTallaDto productoTallaDto);
}
