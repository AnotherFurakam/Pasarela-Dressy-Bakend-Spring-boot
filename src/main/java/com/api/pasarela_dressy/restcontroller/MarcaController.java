package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.services.Marca.IMarcaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marca")
@Tag(name = "Marca")
public class MarcaController
{
    @Autowired
    IMarcaService marcaService;

    @GetMapping
    public List<MarcaDto> getAllMarcas()
    {
       return marcaService.getAll();
    }

    @PostMapping
    public MarcaDto createMarca(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateMarcaDto marcaDto)
    {
        return marcaService.create(marcaDto);
    }
}
