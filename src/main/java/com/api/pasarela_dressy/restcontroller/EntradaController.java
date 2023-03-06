package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Entrada.CreateEntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaWithDetailsDto;
import com.api.pasarela_dressy.services.Entrada.IEntradaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("entrada")
@Tag(name = "Entrada")
public class EntradaController
{
    @Autowired
    IEntradaService entradaService;

    @GetMapping("/{id_entrada}")
    public EntradaWithDetailsDto getEntradaById(@PathVariable String id_entrada){
        return entradaService.getById(id_entrada);
    }

    @PostMapping
    public EntradaDto createEntrada(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateEntradaDto entradaDto){
        return entradaService.create(entradaDto);
    }
}
