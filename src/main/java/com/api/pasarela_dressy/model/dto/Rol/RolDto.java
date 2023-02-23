package com.api.pasarela_dressy.model.dto.Rol;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RolDto {
    private UUID id_rol;
    private String nombre;
    private LocalDateTime creado_el;
}
