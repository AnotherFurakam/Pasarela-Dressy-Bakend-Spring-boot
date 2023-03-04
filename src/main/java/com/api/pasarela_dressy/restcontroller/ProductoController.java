package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.services.Producto.IProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@Tag(name = "Producto")
public class ProductoController
{
    @Autowired
    IProductoService productoService;

    @GetMapping
    public List<ProductoDto> getAllProductos()
    {
        return productoService.findAll();
    }

    @PostMapping
    public ProductoDto createProducto(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProductoDto createProductoDto)
    {
        return productoService.create(createProductoDto);
    }
}
