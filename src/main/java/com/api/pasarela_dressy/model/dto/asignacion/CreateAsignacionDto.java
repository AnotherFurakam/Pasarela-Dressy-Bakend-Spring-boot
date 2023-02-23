package com.api.pasarela_dressy.model.dto.asignacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateAsignacionDto
{
    @NotNull(message = "El id del empleado no debe ser nulo")
    @NotBlank(message = "El id del empleado no debe estar en blanco")
    private String id_empleado;

    @NotNull(message = "El id del rol no debe ser nulo")
    @NotBlank(message = "El id del rol no debe estar en blanco")
    private String id_rol;
}
