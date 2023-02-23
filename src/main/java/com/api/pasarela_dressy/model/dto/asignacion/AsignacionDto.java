package com.api.pasarela_dressy.model.dto.asignacion;

import com.api.pasarela_dressy.model.dto.Empleado.ShortEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Rol.ShortRolDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AsignacionDto
{
    private UUID id_asignacion;
    private LocalDateTime creado_el;
    private Boolean activo;
    private ShortEmpleadoDto empleado;
    private ShortRolDto rol;
}
