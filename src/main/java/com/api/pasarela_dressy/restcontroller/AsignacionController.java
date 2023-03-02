package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.CreateAsignacionDto;
import com.api.pasarela_dressy.services.Asignacion.AsignacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignacion")
@Tag(name = "Asignacion")
public class AsignacionController
{
    @Autowired
    AsignacionService asignacionService;

    @GetMapping
    public ResponseEntity<List<AsignacionDto>> getAllAsignaciones()
    {
        return new ResponseEntity<>(asignacionService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{id_asignacion}")
    public ResponseEntity<AsignacionDto> getAsignacionById(
        @PathVariable String id_asignacion
    )
    {
        return new ResponseEntity<>(asignacionService.getById(id_asignacion), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AsignacionDto> createAsignacion(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateAsignacionDto asignacion
    )
    {
        return new ResponseEntity<>(asignacionService.create(asignacion), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id_asignacion}")
    public ResponseEntity<AsignacionDto> deleteAsignacion(
        @PathVariable String id_asignacion
    )
    {
        return new ResponseEntity<>(asignacionService.delete(id_asignacion), HttpStatus.OK);
    }

}
