package com.api.pasarela_dressy.model.dto.Producto;

import com.api.pasarela_dressy.model.dto.Categoria.ShortCategoriaDto;
import com.api.pasarela_dressy.model.dto.Marca.ShortMarcaDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductoDto
{
    private UUID id_producto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal precio_oferta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creado_el;
    private String sku;
    private int  stock;
    private ShortMarcaDto marca;
    private ShortCategoriaDto categoria;
    private Boolean activo;
}
