package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;
import com.api.pasarela_dressy.services.Producto.IProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductoDto>> getAllProductos()
    {
        return new ResponseEntity<>(productoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id_producto}")
    public ResponseEntity<ProductoDto> getProductoById(
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.findById(id_producto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductoDto> createProducto(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        CreateProductoDto createProductoDto
    )
    {
        return new ResponseEntity<>(productoService.create(createProductoDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id_producto}")
    public ResponseEntity<ProductoDto> updateProductoById(
        @Valid
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        UpdateProductoDto productoDto,
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.update(productoDto, id_producto), HttpStatus.OK);
    }

    @PostMapping("/disable/{id_producto}")
    public ResponseEntity<ProductoDto> disableProductoById(
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.disable(id_producto), HttpStatus.OK);
    }

    @PostMapping("/enable/{id_producto}")
    public ResponseEntity<ProductoDto> enableProductoById(
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.enable(id_producto), HttpStatus.OK);
    }

    @DeleteMapping("/{id_producto}")
    public ResponseEntity<ProductoDto> deleteProductoById(
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.delete(id_producto), HttpStatus.OK);
    }

    @PostMapping("/restore/{id_producto}")
    public ResponseEntity<ProductoDto> restoreProductoById(
        @PathVariable
        String id_producto
    )
    {
        return new ResponseEntity<>(productoService.restore(id_producto), HttpStatus.OK);
    }
}
