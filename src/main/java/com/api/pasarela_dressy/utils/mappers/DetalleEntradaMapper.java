package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.DetalleEntrada.CreateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.DetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.DetalleEntrada.UpdateDetalleEntradaDto;
import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DetalleEntradaMapper
{
    @Autowired
    ModelMapper mapper;


    /**
     * Convierte la entidad de talla a un short dto
     * @param tallaEntity TallaEntity
     * @return ShortTallaDto
     */
    private ShortTallaDto toShortTallaDto (TallaEntity tallaEntity)
    {
        return mapper.map(tallaEntity, ShortTallaDto.class);
    }

    /**
     * Mapea los datos de la entidad a ShortProductoDto
     * @param productoEntity ProductoEntity
     * @return ShortProductoDto
     */
    private ShortProductoDto toShortProductoDto (ProductoEntity productoEntity)
    {
        ShortProductoDto productoDto = mapper.map(productoEntity, ShortProductoDto.class);
        productoDto.setCategoria(productoEntity.getCategoria().getNombre());
        productoDto.setMarca(productoEntity.getMarca().getNombre());
        return productoDto;
    }


    /**
     * Mapea los datos del detalle de entrada al DetalleEntradaDto
     * @param detalleEntradaEntity DetalleEntradaEntity
     * @return DetalleEntradaDto
     */
    public DetalleEntradaDto toDto(DetalleEntradaEntity detalleEntradaEntity){
        DetalleEntradaDto dto = mapper.map(detalleEntradaEntity, DetalleEntradaDto.class);
        dto.setTalla(this.toShortTallaDto(detalleEntradaEntity.getTalla()));
        dto.setProducto(this.toShortProductoDto(detalleEntradaEntity.getProducto()));
        return dto;
    }

    /**
     * Mapea los dato del dto del detalle de entrada a DetalleDeEntradaEntity
     * @param detalleEntradaDto CreateDetalleEntradaDto
     * @return DetalleEntradaEntity
     */
    public DetalleEntradaEntity toEntity(CreateDetalleEntradaDto detalleEntradaDto)
    {
        return mapper.map(detalleEntradaDto, DetalleEntradaEntity.class);
    }

    /**
     * Actualiza los datos de la entidad desde el dto haciendo uso del mapeado
     * @param detalleEntradaDto UpdateDetalleEntradaDto
     * @param detalleEntradaEntity DetalleEntradaEntity
     */
    public void updateFromDto(UpdateDetalleEntradaDto detalleEntradaDto, DetalleEntradaEntity detalleEntradaEntity)
    {
        mapper.map(detalleEntradaDto, detalleEntradaEntity);
    }

}
