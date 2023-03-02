package com.api.pasarela_dressy.model.dto.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearRolDto {
    @Size(min = 3, max = 30, message = "El nombre debe tener como mínimo {min} y como máximo {max} caracteres")
    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe estar vacío")
    @Pattern(regexp = "^[a-z_a-z]{3,30}$", message = "Formato de rol incorrecto. El formato correcto es: 'ADMIN', 'admin' o 'ADMIN_ALMACEN'.")
    @Schema(name = "nombre", example = "ADMINISTRADOR")
    private String nombre;
}
