package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;
import com.api.pasarela_dressy.services.ProductoTalla.IProductoTallaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("producto-talla")
@Tag(name = "Producto - Talla")
public class ProductoTallaController
{

    @Autowired
    IProductoTallaService productoTallaService;

    @PostMapping("/agregar")
    public ProductoTallaDto createProductoTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProductoTallaDto productoTallaDto)
    {
        return productoTallaService.create(productoTallaDto);
    }

    @PutMapping("/disminuir")
    public ProductoTallaDto reduceProductoTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProductoTallaDto productoTallaDto)
    {
        return productoTallaService.update(productoTallaDto);
    }
}
