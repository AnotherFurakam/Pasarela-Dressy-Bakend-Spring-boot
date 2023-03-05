package com.api.pasarela_dressy.model.dto.Producto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortProductoDto
{
    private UUID id_producto;
    private String nombre;
    private String sku;
    private Integer stock;
    private String marca;
    private String categoria;
}
