package com.api.pasarela_dressy.model.dto.Empleado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto
{
    @Size(min = 8, max = 20, message = "La contraseña debe tener como mínimo {min} y máximo {max} caracteres")
    @NotBlank(message = "La contraseña no debe estar en blanco")
    @NotNull(message = "La contraseña no debe ser nulo")
    private String contrasenia;
}
