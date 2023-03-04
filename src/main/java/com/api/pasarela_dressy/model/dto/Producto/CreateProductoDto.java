package com.api.pasarela_dressy.model.dto.Producto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateProductoDto
{
    @Size(min = 2, max = 300, message = "El nombre debe tener como mínimo {min} y máximo {max} caracteres")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Schema(name = "nombre", example = "Polo para hombre oversize Bippers manga corta negro")
    private String nombre;

    @Size(max = 500, message = "La descripción debe tener como máximo {max} caracteres")
    @NotBlank(message = "La descripción no puede estar en blanco")
    @Schema(name = "descripcion", example = "Luce tu mejor estilo y sientete cómodo, fresco y a la moda con este polo Bippers")
    private String descripcion;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal precio;

    @DecimalMin(value = "0", message = "El mínimo valor aceptado es cero")
    private BigDecimal precio_oferta;

    @NotBlank(message = "El id de la marca no puede estar en blanco")
    @UUID(message = "Id de categoría no válido")
    private String id_marca;

    @NotBlank(message = "El id de la categoría no puede estar en blanco")
    @UUID(message = "Id de categoría no válido")
    private String id_categoria;
}
