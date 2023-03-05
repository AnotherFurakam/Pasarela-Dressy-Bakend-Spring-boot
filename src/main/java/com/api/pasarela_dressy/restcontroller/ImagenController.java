package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;
import com.api.pasarela_dressy.services.Imagen.IImagenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ImagenDto createImagen(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateImagenDto imagenDto)
    {
        return imagenService.createImagen(imagenDto);
    }

    @GetMapping("/producto/{id_producto}")
    public List<ImagenDto> getAllImagenesByIdProducto(@PathVariable String id_producto)
    {
        return imagenService.getImagenByIdProducto(id_producto);
    }

    @DeleteMapping("{id_imagen}")
    public ImagenDto deleteImagenById(@PathVariable String id_imagen)
    {
        return imagenService.deleteImagenById(id_imagen);
    }
}
