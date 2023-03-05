package com.api.pasarela_dressy.model.dto.Talla;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTallaDto
{
    @Size(min = 1, max = 10, message = "El nombre debe tener mínimo {min} y máximo {max} caracteres")
    @NotBlank(message = "El nombre no debe estar vacío ni ser nulo")
    @Pattern(
        regexp = "^[A-Z1-9]*$",
        message = "Solo se permiten mayúsculas y no se permiten caracteres especiales ni espacios en el nombre de la talla"
    )
    @Schema(name = "nombre", example = "XL")
    private String nombre;

}
