package com.api.pasarela_dressy.services.Imagen;

import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;

import java.util.List;

public interface IImagenService
{
    ImagenDto createImagen(CreateImagenDto imagenDto);

    List<ImagenDto> getImagenByIdProducto(String id_producto);

    ImagenDto deleteImagenById(String id_imagen);

}
