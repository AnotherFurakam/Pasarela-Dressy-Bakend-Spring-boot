package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.CreateAsignacionDto;
import com.api.pasarela_dressy.services.Asignacion.AsignacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asignacion")
@Tag(name = "Asignacion")
public class AsignacionController
{
    @Autowired
    AsignacionService asignacionService;

    @PostMapping
    public ResponseEntity<AsignacionDto> createAsignacion(CreateAsignacionDto asignacion){
        return new ResponseEntity<>(asignacionService.create(asignacion), HttpStatus.CREATED);
    }
}
