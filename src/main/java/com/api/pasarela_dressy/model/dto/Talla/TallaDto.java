package com.api.pasarela_dressy.model.dto.Talla;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TallaDto
{
    private UUID id_talla;
    private String nombre;
    private LocalDateTime creado_el;
}
