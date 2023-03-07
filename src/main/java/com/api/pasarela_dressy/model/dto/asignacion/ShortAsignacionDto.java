package com.api.pasarela_dressy.model.dto.asignacion;

import com.api.pasarela_dressy.model.dto.Rol.ShortRolDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShortAsignacionDto
{
    private UUID id_asignacion;
    private ShortRolDto rol;
}
