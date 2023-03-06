package com.api.pasarela_dressy.services.DetalleEntrada;


import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;

public interface IDetalleEntradaService
{
    DetalleEntradaDto create(CreateDetalleEntradaDto detalleEntradaDto);
}
