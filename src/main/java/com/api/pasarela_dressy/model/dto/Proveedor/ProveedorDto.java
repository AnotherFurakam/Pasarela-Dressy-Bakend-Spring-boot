package com.api.pasarela_dressy.model.dto.Proveedor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProveedorDto
{
    private UUID id_proveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDateTime creado_el;
}
