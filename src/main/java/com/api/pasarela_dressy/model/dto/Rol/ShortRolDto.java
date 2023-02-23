package com.api.pasarela_dressy.model.dto.Rol;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortRolDto
{
    private UUID id_rol;
    private String nombre;
}
