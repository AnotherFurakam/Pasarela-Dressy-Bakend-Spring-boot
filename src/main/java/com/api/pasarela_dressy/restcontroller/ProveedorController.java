package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;
import com.api.pasarela_dressy.services.Proveedor.IProveedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProveedorDto createProveedor(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProveedorDto proveedorDto)
    {
        return proveedorService.create(proveedorDto);
    }

    @GetMapping
    public List<ProveedorDto> getAllProveedores()
    {
        return proveedorService.getAll();
    }


    @PutMapping("/{id_proveedor}")
    public ProveedorDto updateProveedorById(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProveedorDto proveedorDto,@PathVariable String id_proveedor)
    {
        return proveedorService.update(proveedorDto, id_proveedor);
    }

    @DeleteMapping("/{id_proveedor}")
    public ProveedorDto deleteProveedorById(@PathVariable  String id_proveedor){
        return proveedorService.delete(id_proveedor);
    }


    @PostMapping("/restore/{id_proveedor}")
    public ProveedorDto restoreProveedorById(@PathVariable  String id_proveedor){
        return proveedorService.restore(id_proveedor);
    }

}
