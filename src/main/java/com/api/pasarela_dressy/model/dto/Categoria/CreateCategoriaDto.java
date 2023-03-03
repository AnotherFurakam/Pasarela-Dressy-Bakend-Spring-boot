package com.api.pasarela_dressy.model.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoriaDto
{
    @Size(
        min = 3,
        max = 50,
        message = "El nombre debe tener como mínimo {min} y máximo {max} caracteres"
    )
    @NotBlank(message = "El nombre no debe ser nulo ni estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]?[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*[a-zA-ZáéíóúÁÉÍÓÚñÑ]$",
        message = "No se permiten caracteres especiales, espacios al inicio y final, y números en el nombre"
    )
    @Schema(name = "nombre", example = "Polos")
    private String nombre;
}
