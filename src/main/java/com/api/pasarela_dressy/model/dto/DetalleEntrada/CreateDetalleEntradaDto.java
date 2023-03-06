package com.api.pasarela_dressy.model.dto.DetalleEntrada;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
public class CreateDetalleEntradaDto
{
    @Min(value = 1, message = "La cantidad mínima es 1")
    private int cantidad;

    @UUID(message = "Debe ingresar un id de entrada válido")
    private String id_entrada;

    @UUID(message = "Debe ingresar un id de talla válido")
    private String id_talla;

    @UUID(message = "Debe ingresar un id de producto válido")
    private String id_producto;
}
