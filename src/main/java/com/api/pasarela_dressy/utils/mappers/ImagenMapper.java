package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;
import com.api.pasarela_dressy.model.entity.ImagenEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImagenMapper
{
    @Autowired
    ModelMapper mapper;

    public ImagenDto toDto(ImagenEntity imagenEntity)
    {
        ImagenDto dto = mapper.map(imagenEntity, ImagenDto.class);
        dto.setId_producto(imagenEntity.getProducto().getId_producto());
        return dto;
    }

    public ImagenEntity toEntity(CreateImagenDto createImagenDto)
    {
        return mapper.map(createImagenDto, ImagenEntity.class);
    }

    public List<ImagenDto> imagenDtoList(List<ImagenEntity> imagenEntityList)
    {
        return imagenEntityList.stream().map(i -> {
                ImagenDto imagen = mapper.map(i, ImagenDto.class);
                imagen.setId_producto(i.getProducto().getId_producto());
                return imagen;
            }).collect(Collectors.toList());
    }
}
