package com.api.pasarela_dressy.services.ProductoTalla;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.CreateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.ProductoTallaDto;
import com.api.pasarela_dressy.model.dto.ProductoTalla.UpdateProductoTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.ProductoTallaEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.repository.ProductoTallaRepository;
import com.api.pasarela_dressy.repository.TallaRepository;
import com.api.pasarela_dressy.services.Producto.ProductoServiceImp;
import com.api.pasarela_dressy.services.Talla.TallaServiceImp;
import com.api.pasarela_dressy.utils.mappers.ProductoTallaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

//TODO: Evaluar si es necesario tener un controlador para este serivico
@Service
public class ProductoTallaServiceImp implements IProductoTallaService
{
    @Autowired
    ProductoTallaRepository productoTallaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoTallaMapper productoTallaMapper;

    @Autowired
    ProductoServiceImp productoServiceImp;

    @Autowired
    TallaServiceImp tallaServiceImp;

    @Override
    public ProductoTallaDto create(DetalleEntradaEntity detalleEntradaEntity)
    {
        ProductoEntity producto = detalleEntradaEntity.getProducto();
        TallaEntity talla = detalleEntradaEntity.getTalla();
        int cantidad = detalleEntradaEntity.getCantidad();

        if (talla.getEliminado()) throw new BadRequestException("La talla esta eliminada");

        //* Aumentando el stock del producto
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);

        //Buscando si existe un productoTalla reigstrado con el mismo producto y talla
        ProductoTallaEntity findedPT = productoTallaRepository.getByProductoAndTalla(producto, talla);
        if (findedPT != null && !findedPT.getEliminado())
        {
            //* Actualizando la cantidad del producto talla en caso se encontrase y no esté eliminado
            findedPT.setCantidad(findedPT.getCantidad() + cantidad);

            return productoTallaMapper.toDto(productoTallaRepository.save(findedPT));
        }

        ProductoTallaEntity productoTallaEntity = new ProductoTallaEntity();
        productoTallaEntity.setProducto(producto);
        productoTallaEntity.setTalla(talla);
        productoTallaEntity.setCantidad(cantidad);

        return productoTallaMapper.toDto(productoTallaRepository.save(productoTallaEntity));
    }

    @Override
    public ProductoTallaDto update(UpdateProductoTallaDto productoTallaDto)
    {
        ProductoEntity producto = productoServiceImp.getProductoById(productoTallaDto.getId_producto());
        if (producto.getEliminado()) throw new BadRequestException("El producto esta eliminado");

        TallaEntity talla = tallaServiceImp.getTallaById(productoTallaDto.getId_talla());
        if (talla.getEliminado()) throw new BadRequestException("La talla esta eliminada");

        ProductoTallaEntity findedPT = productoTallaRepository.getByProductoAndTalla(producto, talla);
        if (findedPT == null)
            throw new BadRequestException("Aún no ha registrado el producto con la talla correspondiente");

        //* Disminuyendo la cantidad del producto talla
        int cantidadRestantePT = findedPT.getCantidad() - productoTallaDto.getCantidad();
        if (cantidadRestantePT < 0)
            throw new BadRequestException("La cantidad de productos con talla " + talla.getNombre() + " supera el stock de la talla");

        findedPT.setCantidad(cantidadRestantePT);

        //* Actualizando el stock principal de producto
        producto.setStock(producto.getStock() - productoTallaDto.getCantidad());
        productoRepository.save(producto);

        return productoTallaMapper.toDto(productoTallaRepository.save(findedPT));
    }
}
