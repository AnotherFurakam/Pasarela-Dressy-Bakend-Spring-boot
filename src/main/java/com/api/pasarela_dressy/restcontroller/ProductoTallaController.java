package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;
import com.api.pasarela_dressy.services.ProductoTalla.IProductoTallaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("producto-talla")
@Tag(name = "Producto - Talla")
public class ProductoTallaController
{

    @Autowired
    IProductoTallaService productoTallaService;

    /*@PostMapping("/agregar")
    public ResponseEntity<ProductoTallaDto> createProductoTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateProductoTallaDto productoTallaDto)
    {
        return new ResponseEntity<>(productoTallaService.create(productoTallaDto), HttpStatus.OK);
    }*/

    /*
    @PutMapping("/disminuir")
    public ResponseEntity<ProductoTallaDto> reduceProductoTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateProductoTallaDto productoTallaDto)
    {
        return new ResponseEntity<>(productoTallaService.update(productoTallaDto), HttpStatus.OK);
    }*/
}
