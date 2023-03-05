package com.api.pasarela_dressy.model.dto.Proveedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateProveedorDto
{
    @Size(min = 4, max = 100, message = "El nombre debe ser de mínimo {min} y maximo {max} caracteres")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[a-z A-Z]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]$",
        message = "No se permiten caracteres especiales ni números en el nombre"
    )
    @Schema(name = "nombre", example = "Importaciones moda crazy")
    private String nombre;

    @Size(
        min = 5,
        max = 400,
        message = "La dirección debe tener como mínimo {min} y máximo {max} caracteres"
    )
    @NotBlank(message = "La dirección no debe ser nula ni estar en blanco")
    @Schema(name = "direccion", example = "Av. Tulipanes, calle las ortigas Mz D2 Lote 13")
    private String direccion;

    @Size(min = 9, max = 9, message = "El teléfono de celular debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El teléfono de celular no puede ser nulo")
    @NotBlank(message = "El teléfono de celular no puede estar en blanco")
    @Pattern(
        regexp = "^9[0-9]{8}$",
        message = "Teléfono no válido"
    )
    @Schema(name = "telefono", example = "998493849")
    private String telefono;
}
