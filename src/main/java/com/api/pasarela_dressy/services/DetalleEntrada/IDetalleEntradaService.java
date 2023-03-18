package com.api.pasarela_dressy.services.DetalleEntrada;


import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.UpdateDetalleEntradaDto;

public interface IDetalleEntradaService
{
    DetalleEntradaDto getById(String id_detalle_entrada);
    DetalleEntradaDto create(CreateDetalleEntradaDto detalleEntradaDto);

    DetalleEntradaDto update(UpdateDetalleEntradaDto detalleEntradaDto, String id_detalle_entrada);
}
