package com.api.pasarela_dressy.model.dto.Empleado;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateEmpleadoDto
{
    @Size(min = 2, max = 50, message = "El nombre debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String nombres;
    @Size(min = 2, max = 60, message = "El apellido paterno debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El apellido paterno no puede ser nulo")
    @NotBlank(message = "El apellodo paterno no puede estar en blanco")
    private String apellido_pat;
    @Size(min = 2, max = 60, message = "El apellido_materno debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El apellido materno no puede ser nulo")
    @NotBlank(message = "El apellido materno no puede estar en blanco")
    private String apellido_mat;
    @Size(min = 8, max = 8, message = "El dni debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El dni no puede ser nulo")
    @NotBlank(message = "El dni no puede estar en blanco")
    private String dni;
    @Size(min = 9, max = 9, message = "El número de celular debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El número de celular no puede ser nulo")
    @NotBlank(message = "El número de celular no puede estar en blanco")
    private String numero_cel;
    @Size(min = 5, max = 60, message = "El correo electrónico debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "El correo electrónico no puede ser nulo")
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El campo correo debe ser un correo electrócino")
    private String correo;
    @Size(min = 5, max = 300, message = "La dirección debe ser de mínimo {min} y maximo {max} caracteres")
    @NotNull(message = "La dirección no puede ser nulo")
    @NotBlank(message = "La dirección no puede estar en blanco")
    private String direccion;
}
