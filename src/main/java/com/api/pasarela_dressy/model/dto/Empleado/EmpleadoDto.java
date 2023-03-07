package com.api.pasarela_dressy.model.dto.Empleado;

import com.api.pasarela_dressy.model.dto.Rol.ShortRolDto;
import com.api.pasarela_dressy.model.dto.asignacion.ShortAsignacionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EmpleadoDto
{
    private UUID id_empleado;
    private String nombres;
    private String apellido_pat;
    private String apellido_mat;
    private String dni;
    private String numero_cel;
    private String correo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creado_el;
    private String direccion;
    private Boolean activo;
    private List<ShortAsignacionDto> roles;
}
