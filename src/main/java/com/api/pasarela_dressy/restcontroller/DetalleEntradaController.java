package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.services.DetalleEntrada.IDetalleEntradaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("detalle-entrada")
@Tag(name = "Detalle entrada")
public class DetalleEntradaController
{
    @Autowired
    IDetalleEntradaService detalleEntradaService;

    @PostMapping
    public DetalleEntradaDto createDetalleEntrada(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateDetalleEntradaDto detalleEntradaDto){
        return detalleEntradaService.create(detalleEntradaDto);
    }

}
