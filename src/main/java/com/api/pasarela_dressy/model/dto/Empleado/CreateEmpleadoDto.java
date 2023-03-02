package com.api.pasarela_dressy.model.dto.Empleado;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateEmpleadoDto
{
    @Size(min = 2, max = 50, message = "El nombre debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[a-z A-Z]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]$",
        message = "No se permiten caracteres especiales ni números en el nombre"
    )
    @Schema(name = "nombres", description = "Dto para crear la marca del producto", example = "Ernesto Julio")
    private String nombres;

    @Size(min = 2, max = 60, message = "El apellido paterno debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El apellido paterno no puede ser nulo")
    @NotBlank(message = "El apellodo paterno no puede estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[a-z A-Z]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]$",
        message = "No se permiten caracteres especiales ni números en el apellido paterno"
    )
    @Schema(name = "apellido_pat", example = "Martinez")
    private String apellido_pat;

    @Size(min = 2, max = 60, message = "El apellido_materno debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El apellido materno no puede ser nulo")
    @NotBlank(message = "El apellido materno no puede estar en blanco")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[a-z A-Z]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]$",
        message = "No se permiten caracteres especiales ni números en el apellido materno"
    )
    @Schema(name = "apellido_mat", example = "Peralta")
    private String apellido_mat;

    @Size(min = 8, max = 8, message = "El dni debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El dni no puede ser nulo")
    @NotBlank(message = "El dni no puede estar en blanco")
    @Pattern(
        regexp = "^[0-9]{8}$",
        message = "Número de dni no válido"
    )
    @Schema(name = "dni", example = "74837483")
    private String dni;

    @Size(min = 9, max = 9, message = "El número de celular debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El número de celular no puede ser nulo")
    @NotBlank(message = "El número de celular no puede estar en blanco")
    @Pattern(
        regexp = "^9[0-9]{8}$",
        message = "Número de celular no válido"
    )
    @Schema(name = "numero_cel", example = "998493849")
    private String numero_cel;

    @Size(min = 5, max = 60, message = "El correo electrónico debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El correo electrónico no puede ser nulo")
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Pattern(
        regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+",
        message = "Correo electrónico no válido"
    )
    @Schema(name = "correo", example = "ernepe@gmail.com")
    private String correo;

    @Size(min = 5, max = 300, message = "La dirección debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "La dirección no puede ser nulo")
    @NotBlank(message = "La dirección no puede estar en blanco")
    @Schema(name = "direccion", example = "Av. Tulipanes, calle las ortigas Mz D2 Lote 13")
    private String direccion;
}
