package com.api.pasarela_dressy.model.dto.Marca;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MarcaDto
{
    private UUID id_marca;
    private String nombre;
    private LocalDateTime creado_el;
}
