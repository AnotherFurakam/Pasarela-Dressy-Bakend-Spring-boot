package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Empleado.CreateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.services.Empleado.EmpleadoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
@Tag(name="Empleado")
public class EmpleadoController
{
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping()
    public ResponseEntity<List<EmpleadoDto>> getAllEmpleados(){
        return new ResponseEntity<>(empleadoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id_empleado}")
    public ResponseEntity<EmpleadoDto> getAllEmpleadoById(
        @PathVariable String id_empleado
    )
    {
        return new ResponseEntity<>(empleadoService.getById(id_empleado), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EmpleadoDto> createEmpleado(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateEmpleadoDto empleado
        )
    {
        return new ResponseEntity<>(empleadoService.create(empleado), HttpStatus.CREATED);
    }

    @PutMapping("/{id_empleado}")
    public ResponseEntity<EmpleadoDto> updateEmpleado(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateEmpleadoDto empleado,
        @PathVariable String id_empleado
    )
    {
        return  new ResponseEntity<>(empleadoService.update(empleado, id_empleado), HttpStatus.OK);
    }

    @DeleteMapping("/{id_empleado}")
    public ResponseEntity<EmpleadoDto> deleteEmpleado(
        @PathVariable String id_empleado
    )
    {
        return new ResponseEntity<>(empleadoService.delete(id_empleado), HttpStatus.OK);
    }

    @PostMapping("/restore/{id_empleado}")
    public ResponseEntity<EmpleadoDto> restoreEmpleado(
        @PathVariable String id_empleado
    )
    {
        return new ResponseEntity<>(empleadoService.restore(id_empleado), HttpStatus.OK);
    }

    @PostMapping("/disable/{id_empleado}")
    public ResponseEntity<EmpleadoDto> disableEmpleado(
        @PathVariable String id_empleado
    )
    {
        return new ResponseEntity<>(empleadoService.disable(id_empleado), HttpStatus.OK);
    }

    @PostMapping("/enable/{id_empleado}")
    public ResponseEntity<EmpleadoDto> enableEmpleado(
        @PathVariable String id_empleado
    )
    {
        return new ResponseEntity<>(empleadoService.enable(id_empleado), HttpStatus.OK);
    }
}
