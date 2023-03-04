package com.api.pasarela_dressy.services.Producto;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Categoria.ShortCategoriaDto;
import com.api.pasarela_dressy.model.dto.Marca.ShortMarcaDto;
import com.api.pasarela_dressy.model.dto.Producto.CreateProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.ProductoDto;
import com.api.pasarela_dressy.model.dto.Producto.UpdateProductoDto;
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
            ProductoDto producto = mapper.map(p, ProductoDto.class);

            //Mapeando la marca de cada producto al dto de marca de la respuesta
            producto.setMarca(mapper.map(p.getMarca(), ShortMarcaDto.class));

            //Mapeando la categoría de cada producto al dto de categoría de la respuesta
            producto.setCategoria(mapper.map(p.getCategoria(), ShortCategoriaDto.class));

            //Retornando el producto dto
            return producto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductoDto findById(String id_producto)
    {
        try
        {
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            ProductoDto productoDto = mapper.map(producto, ProductoDto.class);
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoDto update(UpdateProductoDto updateProductoDto, String id_producto)
    {
        try
        {
            //Obteniendo el producto a actualizar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            //Actualizando los datos del producto encontrado con los datos del dto
            mapper.map(updateProductoDto, producto);

            //Verificando si se actualizó la marca
            if (!producto.getMarca().getId_marca().toString().equals(updateProductoDto.getId_marca()))
            {
                MarcaEntity marca = marcaRepository.findById(UUID.fromString(updateProductoDto.getId_marca())).orElseThrow(() -> new NotFoundException("La marca con el id " + updateProductoDto.getId_marca() + " no fue encontrada"));

                producto.setMarca(marca);

                //Generando un nuevo código sku para el producto
                producto.setSku(skuGenerate.createSkuCode(producto, producto.getMarca(), producto.getCategoria()));
            }

            //Verificando si se actualio la categoría
            if (!producto.getCategoria().getId_categoria().toString().equals(updateProductoDto.getId_categoria()))
            {
                //En caso de que sea diferente a la que se tenia obtendremos la información de esa categoria
                CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(updateProductoDto.getId_categoria())).orElseThrow(() -> new NotFoundException("La categoría con el id " + updateProductoDto.getId_categoria() + " no fue econtrada"));

                //Si se encontró la categoria
                producto.setCategoria(categoria);

                //Generando un nuevo código sku para el producto
                producto.setSku(skuGenerate.createSkuCode(producto, producto.getMarca(), producto.getCategoria()));
            }

            //Mapeando al dto de respuesta la información ya actualizada y guardada del producto
            ProductoDto productoDto = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Mapeando los datos de la categoria en el dto de respuesta correspondiente
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));

            //Mapeando los datos de la marca en el dto de respuesta correspondiente
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            //retornando el dto de respuesta
            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoDto disable(String id_producto)
    {
        try
        {
            //Obteniendo el producto a actualizar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            if (!producto.getActivo()) throw new BadRequestException("El producto ya fue desabilitado");

            //Desactivando o desabilitando el producto
            producto.setActivo(false);

            //Mapeando al dto de respuesta la información ya actualizada y guardada del producto
            ProductoDto productoDto = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Mapeando los datos de la categoria en el dto de respuesta correspondiente
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));

            //Mapeando los datos de la marca en el dto de respuesta correspondiente
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            //retornando el dto de respuesta
            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoDto enable(String id_producto)
    {
        try
        {
            //Obteniendo el producto a actualizar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            if (producto.getActivo()) throw new BadRequestException("El producto esta habilitado");

            //Habilitando o activando el producto
            producto.setActivo(true);

            //Mapeando al dto de respuesta la información ya actualizada y guardada del producto
            ProductoDto productoDto = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Mapeando los datos de la categoria en el dto de respuesta correspondiente
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));

            //Mapeando los datos de la marca en el dto de respuesta correspondiente
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            //retornando el dto de respuesta
            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoDto delete(String id_producto)
    {
        try
        {
            //Obteniendo el producto a actualizar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            if (producto.getEliminado()) throw new BadRequestException("El producto esta eliminado");

            //Desabilitando o desactivando el producto
            producto.setActivo(false);

            //Eliminando el producto de forma lógica
            producto.setEliminado(true);

            //Mapeando al dto de respuesta la información ya actualizada y guardada del producto
            ProductoDto productoDto = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Mapeando los datos de la categoria en el dto de respuesta correspondiente
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));

            //Mapeando los datos de la marca en el dto de respuesta correspondiente
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            //retornando el dto de respuesta
            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoDto restore(String id_producto)
    {
        try
        {
            //Obteniendo el producto a actualizar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(id_producto)).orElseThrow(() -> new NotFoundException("Producto con el id " + id_producto + " no econtrado"));

            if (!producto.getEliminado()) throw new BadRequestException("El producto no está eliminado");

            //Restaurando el producto de forma lógica
            producto.setEliminado(false);

            //Mapeando al dto de respuesta la información ya actualizada y guardada del producto
            ProductoDto productoDto = mapper.map(productoRepository.save(producto), ProductoDto.class);

            //Mapeando los datos de la categoria en el dto de respuesta correspondiente
            productoDto.setCategoria(mapper.map(producto.getCategoria(), ShortCategoriaDto.class));

            //Mapeando los datos de la marca en el dto de respuesta correspondiente
            productoDto.setMarca(mapper.map(producto.getMarca(), ShortMarcaDto.class));

            //retornando el dto de respuesta
            return productoDto;
        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
