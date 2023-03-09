package com.api.pasarela_dressy.services.Producto;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;
import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.services.Categoria.CategoriaServiceImp;
import com.api.pasarela_dressy.services.Marca.MarcaServiceImp;
import com.api.pasarela_dressy.utils.SkuGenerate;
import com.api.pasarela_dressy.utils.mappers.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductoServiceImp implements IProductoService
{
    @Autowired
    CategoriaServiceImp categoriaServiceImp;

    @Autowired
    MarcaServiceImp marcaServiceImp;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoMapper productoMapper;

    @Autowired
    SkuGenerate skuGenerate;

    //* Utils methods

    public ProductoEntity getProductoById(String id_producto)
    {
        try
        {
            return productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("No se encontró el producto"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    //* Service methods

    @Override
    //TODO: Hacer que el nombre del producto sea único
    public ProductoDto create(CreateProductoDto productoDto)
    {
        ProductoEntity producto = productoMapper.toEntity(productoDto);

        CategoriaEntity categoria = categoriaServiceImp.getCategoriaById(productoDto.getId_categoria());

        if (categoria.getEliminado())
            throw new BadRequestException("La categoría fue eliminada, no puede registrar el producto con esta categoría");
        producto.setCategoria(categoria);

        MarcaEntity marca = marcaServiceImp.getMarcaById(productoDto.getId_marca());

        if (marca.getEliminado())
            throw new BadRequestException("La marca fue eliminada, no puede registrar el producto con esta marca");
        producto.setMarca(marca);

        //* Generando el código sku y seteandolo en la entidad producto
        producto.setSku(skuGenerate.createSkuCode(producto));

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public List<ProductoDto> findAll()
    {
        List<ProductoEntity> productos = productoRepository.getAllUndeleted();

        return productoMapper.toListDto(productos);
    }

    @Override
    public ProductoDto findById(String id_producto)
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        return productoMapper.toDto(producto);
    }

    @Override
    public ProductoDto update(
        UpdateProductoDto updateProductoDto,
        String id_producto
    )
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        productoMapper.updateFromDto(updateProductoDto, producto);

        //* Verificando si se actualizó la marca
        if (!producto.getMarca().getId_marca().toString().equals(updateProductoDto.getId_marca()))
        {
            MarcaEntity marca = marcaServiceImp.getMarcaById(updateProductoDto.getId_marca());
            producto.setMarca(marca);

            //Generando un nuevo código sku para el producto
            producto.setSku(skuGenerate.createSkuCode(producto));
        }

        //* Verificando si se actualio la categoría
        if (!producto.getCategoria().getId_categoria().toString().equals(updateProductoDto.getId_categoria()))
        {
            CategoriaEntity categoria = categoriaServiceImp.getCategoriaById(updateProductoDto.getId_categoria());
            producto.setCategoria(categoria);

            //Generando un nuevo código sku para el producto
            producto.setSku(skuGenerate.createSkuCode(producto));
        }

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto disable(String id_producto)
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        if (!producto.getActivo()) throw new BadRequestException("El producto ya fue desabilitado");

        producto.setActivo(false);

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto enable(String id_producto)
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        if (producto.getActivo()) throw new BadRequestException("El producto esta habilitado");

        producto.setActivo(true);

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto delete(String id_producto)
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        if (producto.getEliminado()) throw new BadRequestException("El producto esta eliminado");

        producto.setActivo(false);

        producto.setEliminado(true);

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto restore(String id_producto)
    {
        ProductoEntity producto = this.getProductoById(id_producto);

        if (!producto.getEliminado()) throw new BadRequestException("El producto no está eliminado");

        producto.setEliminado(false);

        return productoMapper.toDto(productoRepository.save(producto));
    }
}
