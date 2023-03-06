package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.Empleado.ShortEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Entrada.CreateEntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaWithDetailsDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ShortProveedorDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.EntradaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntradaMapper
{
    @Autowired
    ModelMapper mapper;

    public EntradaEntity createtoEntity(CreateEntradaDto createEntradaDto)
    {
        return mapper.map(createEntradaDto, EntradaEntity.class);
    }

    public EntradaDto toDto(EntradaEntity entrada)
    {
        EntradaDto dto = mapper.map(entrada, EntradaDto.class);
        dto.setEmpleado(mapper.map(entrada.getEmpleado(), ShortEmpleadoDto.class));
        dto.setProveedor(mapper.map(entrada.getProveedor(), ShortProveedorDto.class));
        return dto;
    }

    public EntradaWithDetailsDto toEntradaWithDetailDto(EntradaEntity entrada, List<DetalleEntradaEntity> detalleEntradaEntityList)
    {
        EntradaWithDetailsDto dto = mapper.map(entrada, EntradaWithDetailsDto.class);
        dto.setDetalle(detalleEntradaEntityList.stream().map(de -> {
            DetalleEntradaDto detalleEntradaDto = mapper.map(de, DetalleEntradaDto.class);
            detalleEntradaDto.setId_entrada(entrada.getId_entrada().toString());
            return detalleEntradaDto;
        }).collect(Collectors.toList()));
        return dto;
    }
}
