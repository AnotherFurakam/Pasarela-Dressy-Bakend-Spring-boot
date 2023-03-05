package com.api.pasarela_dressy.model.dto.Imagen;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ImagenDto
{
    private UUID id_imagen;
    private String url;
    private LocalDateTime creado_el;
    private UUID id_producto;
}
