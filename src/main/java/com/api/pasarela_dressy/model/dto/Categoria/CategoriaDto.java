package com.api.pasarela_dressy.model.dto.Categoria;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoriaDto
{
    private UUID id_categoria;
    private String nombre;
    private LocalDateTime creado_el;
}
