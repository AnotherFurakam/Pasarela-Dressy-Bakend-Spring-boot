package com.api.pasarela_dressy.model.dto.Imagen;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
public class CreateImagenDto
{
    @NotBlank(message = "La url no debe estar vacía ni ser nula")
    @Size(min = 20, max = 400, message = "La url no debe tener menos de {min} y mas de {max} caracteres")
    @URL(message = "Debe ingresar una url válida")
    @Pattern(
        regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_\\+" + ".~#?&\\/\\/=]*)",
        message = "Debe ingresar una URL válida"
    )
    @Schema(name = "url", example = "https://res.cloudinary.com/furakam/image/upload/v1677973713/pasarela-dressy/polo-adidas_y2hjga.webp")
    private String url;


    @NotBlank(message = "El id de producto no debe estar vacía ni ser nulo")
    @UUID(message = "Debe ingresar un id válido")
    private String id_producto;
}
