package com.api.pasarela_dressy.services.Imagen;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.external.imageservice.service.IImageUploaderService;
import com.api.pasarela_dressy.model.dto.Imagen.CreateImagenDto;
import com.api.pasarela_dressy.model.dto.Imagen.ImagenDto;
import com.api.pasarela_dressy.model.entity.ImagenEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.repository.ImagenRepository;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.services.Producto.ProductoServiceImp;
import com.api.pasarela_dressy.utils.mappers.ImagenMapper;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImagenServiceImp
    implements IImagenService
{
    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoServiceImp productoServiceImp;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ImagenMapper imagenMapper;

    @Autowired
    IImageUploaderService imageUploaderService;

    private ImagenEntity getImagenById(String id_imagen)
    {
        try
        {
            return imagenRepository.findById(UUID.fromString(id_imagen)).orElseThrow(
                () -> new NotFoundException("Imagen no encontrada"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    private void existDuplicateUrl(String url)
    {
        List<String> errors = new ArrayList<>();
        ImagenEntity findImagenByUrl = imagenRepository.getImagenByUrl(url);
        if (findImagenByUrl != null)
        {
            errors.add("La url de ingresada ya existe");
            throw new UniqueFieldException(errors);
        }
    }

    @Override
    public List<ImagenDto> createImagen(CreateImagenDto imagenDto)
    {
        //* Buscando el producto al que se le asignara una imagen mediante su id
        ProductoEntity producto = productoServiceImp.getProductoById(imagenDto.getId_producto());

        if (!producto.getActivo()) throw new BadRequestException(
            "El producto no esta activo, no se le podrá asignar una imagen hasta activarlo");

        if (producto.getEliminado()) throw new BadRequestException(
            "El producto esta eliminado, no se le podrá asigar una imagen hasta restaurarlo");

        //* Guardando las imágenes en la nube y obteniendo sus urls
        List<String> urls = imageUploaderService.imageUploader(imagenDto.getImages());

        for (String url : urls)
        {
            ImagenEntity imagen = new ImagenEntity();
            imagen.setProducto(producto);
            imagen.setUrl(url);
            ImagenEntity img = imagenRepository.save(imagen);
        }

        return imagenMapper.imagenDtoList(imagenRepository.getByIdProducto(producto.getId_producto()));
    }

    @Override
    public List<ImagenDto> getImagenByIdProducto(String id_producto)
    {
        ProductoEntity producto = productoServiceImp.getProductoById(id_producto);

        List<ImagenEntity> imagenes = imagenRepository.getByIdProducto(producto.getId_producto());

        return imagenMapper.imagenDtoList(imagenes);
    }

    @Override
    //TODO: Evaluar si es conveniente hacer eliminado lógico en imágenes
    public ImagenDto deleteImagenById(String id_imagen)
    {
        ImagenEntity imagen = this.getImagenById(id_imagen);
        imagenRepository.delete(imagen);
        return imagenMapper.toDto(imagen);
    }
}
