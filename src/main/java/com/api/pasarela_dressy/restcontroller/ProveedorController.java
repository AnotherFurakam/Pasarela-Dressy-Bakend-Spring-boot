package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;
import com.api.pasarela_dressy.services.Proveedor.IProveedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("proveedor")
@Tag(name = "Proveedor")
public class ProveedorController
{
    @Autowired
    IProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorDto> createProveedor(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProveedorDto proveedorDto)
    {
        return new ResponseEntity<>(proveedorService.create(proveedorDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> getAllProveedores()
    {
        return new ResponseEntity<>(proveedorService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/{id_proveedor}")
    public ResponseEntity<ProveedorDto> updateProveedorById(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProveedorDto proveedorDto,@PathVariable String id_proveedor)
    {
        return new ResponseEntity<>(proveedorService.update(proveedorDto, id_proveedor), HttpStatus.OK);
    }

    @DeleteMapping("/{id_proveedor}")
    public ResponseEntity<ProveedorDto> deleteProveedorById(@PathVariable  String id_proveedor){
        return new ResponseEntity<>(proveedorService.delete(id_proveedor), HttpStatus.OK);
    }

    @PostMapping("/restore/{id_proveedor}")
    public ResponseEntity<ProveedorDto> restoreProveedorById(@PathVariable  String id_proveedor){
        return new ResponseEntity<>(proveedorService.restore(id_proveedor), HttpStatus.OK);
    }

}
