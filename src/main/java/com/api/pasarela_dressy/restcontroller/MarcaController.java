package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;
import com.api.pasarela_dressy.services.Marca.IMarcaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MarcaDto>> getAllMarcas()
    {
        return new ResponseEntity<>(marcaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id_marca}")
    public ResponseEntity<MarcaDto> getMarcaById(
        @PathVariable
        String id_marca
    )
    {
        return new ResponseEntity<>(marcaService.getById(id_marca), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MarcaDto> createMarca(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        CreateMarcaDto marcaDto
    )
    {
        return new ResponseEntity<>(marcaService.create(marcaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id_marca}")
    public ResponseEntity<MarcaDto> updateMarca(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        UpdateMarcaDto marcaDto,
        @PathVariable
        String id_marca
    )
    {
        return new ResponseEntity<>(marcaService.update(marcaDto, id_marca), HttpStatus.OK);
    }

    @DeleteMapping("/{id_marca}")
    public ResponseEntity<MarcaDto> deleteMarca(
        @PathVariable
        String id_marca
    )
    {
        return new ResponseEntity<>(marcaService.delete(id_marca), HttpStatus.OK);
    }

    @PostMapping("/restore/{id_marca}")
    public ResponseEntity<MarcaDto> restoreMarca(
        @PathVariable
        String id_marca
    )
    {
        return new ResponseEntity<>(marcaService.restore(id_marca), HttpStatus.OK);
    }
}
