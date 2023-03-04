package com.api.pasarela_dressy.services.Producto;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Categoria.ShortCategoriaDto;
import com.api.pasarela_dressy.model.dto.Marca.ShortMarcaDto;
import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.repository.CategoriaRepository;
import com.api.pasarela_dressy.repository.MarcaRepository;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.utils.SkuGenerate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImp implements IProductoService
{
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    SkuGenerate skuGenerate;

    @Override
    public ProductoDto create(CreateProductoDto productoDto)
    {
        ProductoEntity producto = mapper.map(productoDto, ProductoEntity.class);
        try
        {
            //Evaluando categoría
            CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(productoDto.getId_categoria())).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

            if (categoria.getEliminado())
                throw new BadRequestException("La categoría fue eliminada, no puede registrar el producto con esta categoría");
            //Seteando la categoría encontrada dentro de la entidad producto que se registrara en la bd
            producto.setCategoria(categoria);

            //Evaluando marca
            MarcaEntity marca = marcaRepository.findById(UUID.fromString(productoDto.getId_marca())).orElseThrow(() -> new NotFoundException("Marca no encontrada"));

            if (marca.getEliminado())
                throw new BadRequestException("La marca fue eliminada, no puede registrar el producto con esta marca");
            //Seteando la marca encontrada dentro de la entidad producto que se registrara en la bd
            producto.setMarca(marca);

            //Sku generate
            producto.setSku(skuGenerate.createSkuCode(producto, marca, categoria));

            //Mapeando los datos de producto entity en producto dto para la respuesta
            ProductoDto productoResponse = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Seteando dto de marca en la respuesta del producto
            productoResponse.setCategoria(mapper.map(categoria, ShortCategoriaDto.class));

            //Seteando dto de marca en la respuesta del producto
            productoResponse.setMarca(mapper.map(marca, ShortMarcaDto.class));



            return productoResponse;

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<ProductoDto> findAll()
    {
        List<ProductoEntity> productos = productoRepository.getAllUndeleted();

        //Covirtiendo y retornando los datos de la lista de productos a la lista de dto del producto
        return productos.stream().map(p -> {
            //Mapeando los datos de cada producto al dto de respuesta
            ProductoDto producto = mapper.map(p,ProductoDto.class);

            //Mapeando la marca de cada producto al dto de marca de la respuesta
            producto.setMarca(mapper.map(p.getMarca(),ShortMarcaDto.class));

            //Mapeando la categoría de cada producto al dto de categoría de la respuesta
            producto.setCategoria(mapper.map(p.getCategoria(),ShortCategoriaDto.class));

            //Retornando el producto dto
            return producto;
        }).collect(Collectors.toList());
    }
}
