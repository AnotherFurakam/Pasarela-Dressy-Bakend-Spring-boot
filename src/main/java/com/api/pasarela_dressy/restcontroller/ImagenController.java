package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;
import com.api.pasarela_dressy.services.Imagen.IImagenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagen")
@Tag(name = "Imagen")
public class ImagenController
{
    @Autowired
    IImagenService imagenService;

    @PostMapping()
    public ResponseEntity<ImagenDto> createImagen(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateImagenDto imagenDto)
    {
        return new ResponseEntity<>(imagenService.createImagen(imagenDto), HttpStatus.CREATED);
    }

    @GetMapping("/producto/{id_producto}")
    public ResponseEntity<List<ImagenDto>> getAllImagenesByIdProducto(@PathVariable String id_producto)
    {
        return new ResponseEntity<>(imagenService.getImagenByIdProducto(id_producto), HttpStatus.OK);
    }

    @DeleteMapping("{id_imagen}")
    public ResponseEntity<ImagenDto> deleteImagenById(@PathVariable String id_imagen)
    {
        return new ResponseEntity<>(imagenService.deleteImagenById(id_imagen), HttpStatus.OK);
    }
}
