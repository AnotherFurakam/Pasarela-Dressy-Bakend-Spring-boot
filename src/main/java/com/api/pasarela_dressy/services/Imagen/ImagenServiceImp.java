package com.api.pasarela_dressy.services.Imagen;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;
import com.api.pasarela_dressy.model.entity.ImagenEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.repository.ImagenRepository;
import com.api.pasarela_dressy.repository.ProductoRepository;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImagenServiceImp implements IImagenService
{
    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ImagenDto createImagen(CreateImagenDto imagenDto)
    {
        try
        {
            //Validando si la url de imagen ya fue registrada
            ImagenEntity findImagenByUrl = imagenRepository.getImagenByUrl(imagenDto.getUrl());
            if (findImagenByUrl != null && !findImagenByUrl.getEliminado()) throw new BadRequestException("La url de imagen ya fue registrada");

            //Buscando el producto al que se le asignara una imagen mediante su id
            ProductoEntity producto = productoRepository.findById(UUID.fromString(imagenDto.getId_producto())).orElseThrow(() -> new NotFoundException("El producto con el id " + imagenDto.getId_producto() + " no fue encontrado"));

            //Si el producto no esta activo se lanzará el error correspondiente
            if (!producto.getActivo())
                throw new BadRequestException("El producto no esta activo, no se le podrá asignar una imagen hasta " + "activarlo");

            //Si el producto esta eliminado se lanzará el error correspondiente
            if (producto.getEliminado())
                throw new BadRequestException("El producto esta eliminado, no se le podrá asigar una imagen hasta " + "restaurarlo");

            ImagenEntity imagen = mapper.map(imagenDto, ImagenEntity.class);
            imagen.setProducto(producto);


            ImagenDto imagenResponseDto = mapper.map(imagenRepository.save(imagen), ImagenDto.class);
            imagenResponseDto.setId_producto(producto.getId_producto());

            return imagenResponseDto;

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<ImagenDto> getImagenByIdProducto(String id_producto)
    {
        try
        {
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));
            List<ImagenEntity> imagenes = imagenRepository.getByIdProducto(UUID.fromString(id_producto));
            return imagenes.stream().map(i -> {
                ImagenDto imagen = mapper.map(i, ImagenDto.class);
                imagen.setId_producto(producto.getId_producto());
                return imagen;
            }).collect(Collectors.toList());
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ImagenDto deleteImagenById(String id_imagen)
    {
        try
        {
            ImagenEntity imagen = imagenRepository.findById(UUID.fromString(id_imagen)).orElseThrow(() -> new NotFoundException("La imagen no fue encontrada"));
            imagen.setEliminado(true);
            ImagenDto imagenDto = mapper.map(imagenRepository.save(imagen), ImagenDto.class);
            imagenDto.setId_producto(imagen.getProducto().getId_producto());
            return imagenDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
