package com.api.pasarela_dressy.services.ProductoTalla;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.ProductoTallaEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.repository.ProductoTallaRepository;
import com.api.pasarela_dressy.repository.TallaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductoTallaServiceImp implements IProductoTallaService
{
    @Autowired
    ProductoTallaRepository productoTallaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    TallaRepository tallaRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ProductoTallaDto create(CreateProductoTallaDto productoTallaDto)
    {
        try
        {
            //Buscando el producto a registrar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(productoTallaDto.getId_producto())).orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));


            //Comprobando si el producto esta eliminado
            if (producto.getEliminado()) throw new BadRequestException("El producto esta eliminado");

            //Buscando la talla a registrar
            TallaEntity talla = tallaRepository.findById(UUID.fromString(productoTallaDto.getId_talla())).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            //Comprobando si la talla esta elimnada
            if (talla.getEliminado()) throw new BadRequestException("La talla esta eliminada");

            //Actualizanod el stock del producto
            producto.setStock(producto.getStock() + productoTallaDto.getCantidad());
            productoRepository.save(producto);

            //Buscando si existe un productoTalla reigstrado con el mismo producto y talla
            ProductoTallaEntity findedPT = productoTallaRepository.getByProductoAndTalla(producto, talla);
            if (findedPT != null && !findedPT.getEliminado())
            {
                //Actualizando la cantidad del producto
                findedPT.setCantidad(findedPT.getCantidad() + productoTallaDto.getCantidad());

                //Mapeando los datos para el dto de respuesta
                ProductoTallaDto responseExitDto = mapper.map(productoTallaRepository.save(findedPT), ProductoTallaDto.class);
                //Mapeando datos el objeto producto a devolber en el dto de respuesta
                ShortProductoDto shortProducto = mapper.map(producto, ShortProductoDto.class);

                //Mapeando los datos correspondientes en categoria y marca
                shortProducto.setCategoria(producto.getCategoria().getNombre());
                shortProducto.setMarca(producto.getMarca().getNombre());

                //Seteando el dto de producto en la respuesta
                responseExitDto.setProducto(shortProducto);
                responseExitDto.setTalla(mapper.map(talla, ShortTallaDto.class));

                //Retornando el producto con la cantidad actualizada
                return responseExitDto;
            }

            ProductoTallaEntity productoTallaEntity = mapper.map(productoTallaDto, ProductoTallaEntity.class);

            productoTallaEntity.setProducto(producto);
            productoTallaEntity.setTalla(talla);

            ProductoTallaDto responseDto = mapper.map(productoTallaRepository.save(productoTallaEntity), ProductoTallaDto.class);
            //Mapeando datos el objeto producto a devolber en el dto de respuesta
            ShortProductoDto shortProducto = mapper.map(producto, ShortProductoDto.class);

            //Mapeando los datos correspondientes en categoria y marca
            shortProducto.setCategoria(producto.getCategoria().getNombre());
            shortProducto.setMarca(producto.getMarca().getNombre());

            //Seteando el dto de producto en la respuesta
            responseDto.setProducto(shortProducto);
            responseDto.setTalla(mapper.map(talla, ShortTallaDto.class));

            return responseDto;

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ProductoTallaDto update(UpdateProductoTallaDto productoTallaDto)
    {
        try
        {
            //Buscando el producto a registrar
            ProductoEntity producto = productoRepository.findById(UUID.fromString(productoTallaDto.getId_producto())).orElseThrow(() -> new NotFoundException("El producto no fue encontrado"));


            //Comprobando si el producto esta eliminado
            if (producto.getEliminado()) throw new BadRequestException("El producto esta eliminado");

            //Buscando la talla a registrar
            TallaEntity talla = tallaRepository.findById(UUID.fromString(productoTallaDto.getId_talla())).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            //Comprobando si la talla esta elimnada
            if (talla.getEliminado()) throw new BadRequestException("La talla esta eliminada");

            //Encontando el producto talla a editar
            ProductoTallaEntity findedPT = productoTallaRepository.getByProductoAndTalla(producto, talla);

            if (findedPT == null) throw new BadRequestException("Aún no ha registrado el producto con la talla correspondiente");

            //Calculando el restante de la disminución
            int restantePT = findedPT.getCantidad() - productoTallaDto.getCantidad();
            if (restantePT < 0)
                throw new BadRequestException("La cantidad de productos con talla " + talla.getNombre() + " supera el stock de la talla");

            //Actualizando la cantida de productos por talla
            findedPT.setCantidad(restantePT);


            //Atualizando el stock generar del producto
            producto.setStock(producto.getStock() - productoTallaDto.getCantidad());
            productoRepository.save(producto);

            //Guardando los cambios del ProductoTalla editado
            ProductoTallaDto responseDto = mapper.map(productoTallaRepository.save(findedPT), ProductoTallaDto.class);
            //Mapeando datos el objeto producto a devolber en el dto de respuesta
            ShortProductoDto shortProducto = mapper.map(producto, ShortProductoDto.class);

            //Mapeando los datos correspondientes en categoria y marca
            shortProducto.setCategoria(producto.getCategoria().getNombre());
            shortProducto.setMarca(producto.getMarca().getNombre());

            //Seteando el dto de producto en la respuesta
            responseDto.setProducto(shortProducto);
            responseDto.setTalla(mapper.map(talla, ShortTallaDto.class));

            return responseDto;

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
