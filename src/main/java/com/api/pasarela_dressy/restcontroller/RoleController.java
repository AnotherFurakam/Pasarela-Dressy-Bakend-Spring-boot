package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Rol.CrearRolDto;
import com.api.pasarela_dressy.model.dto.Rol.RolDto;
import com.api.pasarela_dressy.model.dto.Rol.UpdateRolDto;
import com.api.pasarela_dressy.services.Role.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
@Tag(name = "Rol")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RolDto>> getRoles(){
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id_rol}")
    public ResponseEntity<RolDto> getRoleById(
        @PathVariable String id_rol
    )
    {
        return new ResponseEntity<>(roleService.getById(id_rol), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RolDto> createRole(
        //Valid es para validar los campos y el RequestBody del swagger es para que swagger envie la data en json
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CrearRolDto rol
    ){
        return new ResponseEntity<>(roleService.createRole(rol), HttpStatus.CREATED);
    }

    @PutMapping("/{id_rol}")
    public ResponseEntity<RolDto> updateRole(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateRolDto rol,
        @PathVariable String id_rol
    ){
        return new ResponseEntity<>(roleService.updateRole(rol,id_rol), HttpStatus.OK);
    }

    @DeleteMapping("/{id_rol}")
    public ResponseEntity<RolDto> deleteRole(
        @PathVariable String id_rol
    )
    {
        return new ResponseEntity<>(roleService.deleteRole(id_rol), HttpStatus.OK);
    }

}
