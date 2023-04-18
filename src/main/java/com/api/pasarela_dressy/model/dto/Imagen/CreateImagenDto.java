package com.api.pasarela_dressy.model.dto.Imagen;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateImagenDto
{
    private List<MultipartFile> images;

    @NotBlank(message = "El id de producto no debe estar vacía ni ser nulo")
    @UUID(message = "Debe ingresar un id válido")
    private String id_producto;
}
