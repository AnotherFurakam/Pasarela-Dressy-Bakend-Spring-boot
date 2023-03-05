package com.api.pasarela_dressy.model.dto.ProductoTalla;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateProductoTallaDto
{
    @Min(value = 1, message = "La cantidad debe ser mayor a uno")
    private Integer cantidad;

    @org.hibernate.validator.constraints.UUID(message = "Debe ingresar un id válido")
    private String id_producto;

    @org.hibernate.validator.constraints.UUID(message = "Debe ingresar un id válido")
    private String id_talla;
}
