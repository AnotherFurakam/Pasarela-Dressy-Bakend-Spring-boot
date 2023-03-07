package com.api.pasarela_dressy.model.dto.Entrada;

import com.api.pasarela_dressy.model.dto.Empleado.ShortEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ShortProveedorDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EntradaDto
{
    private UUID id_entrada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creado_el;
    private Boolean ejecutado;
    private ShortProveedorDto proveedor;
    private ShortEmpleadoDto empleado;
}
