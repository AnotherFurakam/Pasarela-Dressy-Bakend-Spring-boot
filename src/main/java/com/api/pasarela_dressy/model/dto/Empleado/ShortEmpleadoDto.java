package com.api.pasarela_dressy.model.dto.Empleado;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortEmpleadoDto
{
    private UUID id_empleado;
    private String nombres;
    private String apellido_pat;
    private String apellido_mat;
    private String dni;
}
