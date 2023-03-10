package com.api.pasarela_dressy.model.dto.ProductoTalla;

import com.api.pasarela_dressy.model.dto.Producto.ShortProductoDto;
import com.api.pasarela_dressy.model.dto.Talla.ShortTallaDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductoTallaDto
{
    private UUID id_producto_talla;

    private Integer cantidad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creado_el;

    private ShortProductoDto producto;

    private ShortTallaDto talla;
}
