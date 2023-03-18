package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.services.DetalleEntrada.IDetalleEntradaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("detalle-entrada")
@Tag(name = "Detalle entrada")
public class DetalleEntradaController
{
    @Autowired
    IDetalleEntradaService detalleEntradaService;

    @PostMapping
    public ResponseEntity<DetalleEntradaDto> createDetalleEntrada(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        CreateDetalleEntradaDto detalleEntradaDto
    )
    {
        return new ResponseEntity<>(detalleEntradaService.create(detalleEntradaDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id_detalle_entrada}")
    public ResponseEntity<DetalleEntradaDto> getDetalleEntradaById(
        @Valid
        @PathVariable
        String id_detalle_entrada
    )
    {
        return new ResponseEntity<>(detalleEntradaService.getById(id_detalle_entrada), HttpStatus.OK);
    }

}
