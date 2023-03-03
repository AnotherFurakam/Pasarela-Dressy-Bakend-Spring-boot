package com.api.pasarela_dressy.model.dto.Marca;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMarcaDto
{
    @Size(
        min = 2,
        max = 50,
        message = "El nombre debe tener como mínimo {min} y máximo {max} caracteres"
    )
    @NotBlank(message = "El nombre no debe ser nulo ni estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]?[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*[a-zA-ZáéíóúÁÉÍÓÚñÑ]$",
        message = "No se permiten caracteres especiales, números, ni espacios al inicio y final del nombre"
    )
    @Schema(name = "nombre", example = "Adidas")
    private String nombre;
}
