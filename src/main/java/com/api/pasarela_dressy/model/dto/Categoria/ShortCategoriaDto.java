package com.api.pasarela_dressy.model.dto.Categoria;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortCategoriaDto
{
    private UUID id_categoria;
    private String nombre;
}
