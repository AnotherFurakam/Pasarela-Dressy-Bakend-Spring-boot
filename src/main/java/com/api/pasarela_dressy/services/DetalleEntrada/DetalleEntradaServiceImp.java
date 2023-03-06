package com.api.pasarela_dressy.services.DetalleEntrada;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.EntradaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import com.api.pasarela_dressy.repository.DetalleEntradaRepository;
import com.api.pasarela_dressy.repository.EntradaRepository;
import com.api.pasarela_dressy.repository.ProductoRepository;
import com.api.pasarela_dressy.repository.TallaRepository;
import com.api.pasarela_dressy.utils.mappers.DetalleEntradaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DetalleEntradaServiceImp implements IDetalleEntradaService
{
    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;

    @Autowired
    EntradaRepository entradaRepository;

    @Autowired
    TallaRepository tallaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    DetalleEntradaMapper detalleEntradaMapper;

    @Override
    public DetalleEntradaDto create(CreateDetalleEntradaDto detalleEntradaDto)
    {
        try
        {
            EntradaEntity entrada = entradaRepository.findById(UUID.fromString(detalleEntradaDto.getId_entrada())).orElseThrow(() -> new NotFoundException("La entrada no fue encontrada"));

            TallaEntity talla = tallaRepository.findById(UUID.fromString(detalleEntradaDto.getId_talla())).orElseThrow(() -> new NotFoundException("La talla no fue encontrada"));

            ProductoEntity producto = productoRepository.findById(UUID.fromString(detalleEntradaDto.getId_producto())).orElseThrow(() -> new NotFoundException("El producto no fue encontrada"));

            DetalleEntradaEntity detalleEntradaEntity = mapper.map(detalleEntradaDto, DetalleEntradaEntity.class);
            detalleEntradaEntity.setEntrada(entrada);
            detalleEntradaEntity.setTalla(talla);
            detalleEntradaEntity.setProducto(producto);

            return detalleEntradaMapper.toDto(detalleEntradaRepository.save(detalleEntradaEntity));
        } catch (Exception e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }
}
