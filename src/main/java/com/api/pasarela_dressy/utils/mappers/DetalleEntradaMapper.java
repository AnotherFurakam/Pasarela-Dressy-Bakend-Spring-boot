package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DetalleEntradaMapper
{
    @Autowired
    ModelMapper mapper;

    public DetalleEntradaDto toDto(DetalleEntradaEntity detalleEntradaEntity){
        DetalleEntradaDto dto = mapper.map(detalleEntradaEntity, DetalleEntradaDto.class);
        dto.setId_entrada(detalleEntradaEntity.getEntrada().getId_entrada().toString());
        dto.setTalla(mapper.map(detalleEntradaEntity.getTalla(), ShortTallaDto.class));
        dto.setProducto(mapper.map(detalleEntradaEntity.getProducto(), ShortProductoDto.class));
        return dto;
    }

}
