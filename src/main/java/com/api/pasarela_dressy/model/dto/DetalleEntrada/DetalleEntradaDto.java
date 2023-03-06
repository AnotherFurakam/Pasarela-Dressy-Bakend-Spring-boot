package com.api.pasarela_dressy.model.dto.DetalleEntrada;

import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DetalleEntradaDto
{
    private UUID id_detalle_entrada;
    private int cantidad;
    private String id_entrada;
    private ShortTallaDto talla;
    private ShortProductoDto producto;
}
