package com.api.pasarela_dressy.model.dto.Talla;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortTallaDto
{
    private UUID id_talla;
    private String nombre;
}
