package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;
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

    @GetMapping("/{id_marca}")
    public MarcaDto getMarcaById(@PathVariable String id_marca)
    {
        return marcaService.getById(id_marca);
    }

    @PostMapping
    public MarcaDto createMarca(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateMarcaDto marcaDto)
    {
        return marcaService.create(marcaDto);
    }

    @PutMapping("/{id_marca}")
    public MarcaDto updateMarca(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateMarcaDto marcaDto, @PathVariable String id_marca)
    {
        return marcaService.update(marcaDto, id_marca);
    }

    @DeleteMapping("/{id_marca}")
    public MarcaDto deleteMarca(@PathVariable String id_marca)
    {
        return marcaService.delete(id_marca);
    }

    @PostMapping("/restore/{id_marca}")
    public MarcaDto restoreMarca(@PathVariable String id_marca)
    {
        return marcaService.restore(id_marca);
    }
}
