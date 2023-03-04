package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;
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

    @GetMapping("/{id_producto}")
    public ProductoDto getProductoById(@PathVariable String id_producto)
    {
        return productoService.findById(id_producto);
    }

    @PostMapping
    public ProductoDto createProducto(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProductoDto createProductoDto)
    {
        return productoService.create(createProductoDto);
    }

    @PutMapping("/{id_producto}")
    public ProductoDto updateProductoById(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProductoDto productoDto, @PathVariable String id_producto)
    {
        return productoService.update(productoDto, id_producto);
    }

    @PostMapping("/disable/{id_producto}")
    public ProductoDto disableProductoById(@PathVariable String id_producto)
    {
        return productoService.disable(id_producto);
    }

    @PostMapping("/enable/{id_producto}")
    public ProductoDto enableProductoById(@PathVariable String id_producto)
    {
        return productoService.enable(id_producto);
    }

    @DeleteMapping("/{id_producto}")
    public ProductoDto deleteProductoById(@PathVariable String id_producto)
    {
       return productoService.delete(id_producto);
    }

    @PostMapping("/restore/{id_producto}")
    public ProductoDto restoreProductoById(@PathVariable String id_producto)
    {
        return productoService.restore(id_producto);
    }
}
