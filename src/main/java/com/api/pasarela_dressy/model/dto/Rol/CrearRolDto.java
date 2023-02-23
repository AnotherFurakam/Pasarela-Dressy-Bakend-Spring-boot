package com.api.pasarela_dressy.model.dto.Rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearRolDto {
    @Size(min = 3, max = 30, message = "El nombre debe tener como mínimo {min} y como máximo {max} caracteres")
    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe estar vacío")
    private String nombre;
}
