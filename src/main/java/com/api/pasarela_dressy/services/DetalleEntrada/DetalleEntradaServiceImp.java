package com.api.pasarela_dressy.services.DetalleEntrada;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.UpdateDetalleEntradaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.EntradaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.DetalleEntradaRepository;
import com.api.pasarela_dressy.repository.EntradaRepository;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.repository.TallaRepository;
import com.api.pasarela_dressy.services.Entrada.EntradaServiceImpl;
import com.api.pasarela_dressy.services.Producto.ProductoServiceImp;
import com.api.pasarela_dressy.services.Talla.TallaServiceImp;
import com.api.pasarela_dressy.utils.mappers.DetalleEntradaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DetalleEntradaServiceImp implements IDetalleEntradaService
{
    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;

    @Autowired
    TallaServiceImp tallaServiceImp;

    @Autowired
    ProductoServiceImp productoServiceImp;

    @Autowired
    DetalleEntradaMapper detalleEntradaMapper;

    @Autowired
    EntradaServiceImpl entradaService;

    public DetalleEntradaEntity findDetalleEntradaById(String id_detalle_entrada)
    {
        try
        {
            return detalleEntradaRepository.findById(UUID.fromString(id_detalle_entrada)).orElseThrow(() -> new NotFoundException("Detalle de entrada no encontrado"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    //* Service Methods


    @Override
    public DetalleEntradaDto getById(String id_detalle_entrada)
    {
        DetalleEntradaEntity detalleEntrada = this.findDetalleEntradaById(id_detalle_entrada);
        return detalleEntradaMapper.toDto(detalleEntrada);
    }

    @Override
    public DetalleEntradaDto create(CreateDetalleEntradaDto createDetalleEntradaDto)
    {
        EntradaEntity entrada = entradaService.findEntradaById(createDetalleEntradaDto.getId_entrada());

        TallaEntity talla = tallaServiceImp.getTallaById(createDetalleEntradaDto.getId_talla());

        ProductoEntity producto = productoServiceImp.getProductoById(createDetalleEntradaDto.getId_producto());

        DetalleEntradaEntity detalleEntradaEntity = detalleEntradaMapper.toEntity(createDetalleEntradaDto);
        detalleEntradaEntity.setEntrada(entrada);
        detalleEntradaEntity.setTalla(talla);
        detalleEntradaEntity.setProducto(producto);

        return detalleEntradaMapper.toDto(detalleEntradaRepository.save(detalleEntradaEntity));
    }

    @Override
    public DetalleEntradaDto update(
        UpdateDetalleEntradaDto detalleEntradaDto,
        String id_detalle_entrada
    )
    {
        DetalleEntradaEntity detalleEntrada = this.findDetalleEntradaById(id_detalle_entrada);

        EntradaEntity entrada = entradaService.findEntradaById(detalleEntradaDto.getId_entrada());
        if (entrada.getEjecutado())
            throw new BadRequestException("No se puede editar un detalle si la entrada ya fue ejecutada");

        detalleEntradaMapper.updateFromDto(detalleEntradaDto, detalleEntrada);

        return detalleEntradaMapper.toDto(detalleEntradaRepository.save(detalleEntrada));
    }
}
