package com.api.pasarela_dressy.model.dto.Marca;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortMarcaDto
{
    private UUID id_marca;
    private String nombre;
}
