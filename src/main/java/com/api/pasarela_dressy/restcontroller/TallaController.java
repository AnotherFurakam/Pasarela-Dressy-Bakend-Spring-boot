package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;
import com.api.pasarela_dressy.services.Talla.ITallaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talla")
@Tag(name = "Talla")
public class TallaController
{
    @Autowired
    ITallaService tallaService;

    @GetMapping
    public ResponseEntity<List<TallaDto>> getAllTallas()
    {
        return new ResponseEntity<>(tallaService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TallaDto> createTalla(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        CreateTallaDto createTallaDto
    )
    {
        return new ResponseEntity<>(tallaService.create(createTallaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id_talla}")
    public ResponseEntity<TallaDto> updateTalla(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        UpdateTallaDto updateTallaDto,
        @PathVariable
        String id_talla
    )
    {
        return new ResponseEntity<>(tallaService.update(updateTallaDto, id_talla), HttpStatus.OK);
    }

    @DeleteMapping("/{id_talla}")
    public ResponseEntity<TallaDto> deleteTalla(String id_talla)
    {
        return new ResponseEntity<>(tallaService.delete(id_talla), HttpStatus.OK);
    }


    @PostMapping("/restore/{id_talla}")
    public ResponseEntity<TallaDto> restoreTalla(String id_talla)
    {
        return new ResponseEntity<>(tallaService.restore(id_talla), HttpStatus.OK);
    }
}
