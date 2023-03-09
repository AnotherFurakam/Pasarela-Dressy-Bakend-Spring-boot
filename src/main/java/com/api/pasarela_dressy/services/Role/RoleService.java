package com.api.pasarela_dressy.services.Role;

import com.api.pasarela_dressy.model.dto.Rol.CrearRolDto;
import com.api.pasarela_dressy.model.dto.Rol.RolDto;
import com.api.pasarela_dressy.model.dto.Rol.UpdateRolDto;

import java.util.List;

public interface RoleService {
    List<RolDto> getAll();

    RolDto getById(String id_rol);

    RolDto createRole(CrearRolDto rol);

    RolDto updateRole(UpdateRolDto rol, String id_rol);

    RolDto deleteRole(String id_rol);

    RolDto restoreRole(String id_rol);

}
