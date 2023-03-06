package com.api.pasarela_dressy.model.dto.Entrada;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
public class CreateEntradaDto
{
    @UUID(message = "Debe ingresar un id de proveedor válido")
    private String id_proveedor;

    @UUID(message = "Debe ingresar un id de empleado válido")
    private String id_empleado;
}
