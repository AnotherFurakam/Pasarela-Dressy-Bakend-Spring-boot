package com.api.pasarela_dressy.services.Role;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Rol.CrearRolDto;
import com.api.pasarela_dressy.model.dto.Rol.RolDto;
import com.api.pasarela_dressy.model.dto.Rol.UpdateRolDto;
import com.api.pasarela_dressy.model.entity.RolEntity;
import com.api.pasarela_dressy.repository.RoleRepository;
import jakarta.validation.ConstraintDeclarationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImp implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<RolDto> getAll()
    {

        List<RolEntity> rolesEntities = (List<RolEntity>) roleRepository.findAllUndeleted();

        return rolesEntities.stream().map(r -> mapper.map(r, RolDto.class)).toList();
    }

    @Override
    public RolDto getById(String id_rol)
    {
        try {
            RolEntity rolEntity = roleRepository.findById(UUID.fromString(id_rol)).orElseThrow(() -> new NotFoundException("Rol no encontrado"));

            if (rolEntity.getEliminado() == true) throw new BadRequestException("El rol fue eliminado");

            return mapper.map(rolEntity, RolDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RolDto createRole(CrearRolDto rol)
    {
        try {
            RolEntity rolEntity = mapper.map(rol, RolEntity.class);
            return mapper.map(roleRepository.save(rolEntity), RolDto.class);
        } catch (Exception e) {
            //Verificando si el error corresponde a la violación de un unique constrain
            if (e instanceof DataIntegrityViolationException)
            {
                throw new BadRequestException("El rol '" + rol.getNombre() + "' ya fue creado");

            } else //Cualquier otro tipo de error será procesado obteniendo su propio mensaje
            {
                throw new BadRequestException(e.getCause().getMessage());
            }
        }
    }

    @Override
    public RolDto updateRole(UpdateRolDto rol, String id_rol)
    {
        try {
            RolEntity rolEntity = roleRepository.findById(UUID.fromString(id_rol)).orElseThrow(() -> new NotFoundException("Rol no encontrado"));

            if (rolEntity.getEliminado() == true) throw new BadRequestException("El rol fue eliminado");

            //Mapeando los datos de rolDto en rolEntity
            mapper.map(rol, rolEntity);

            return mapper.map(roleRepository.save(rolEntity), RolDto.class);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RolDto deleteRole(String id_rol)
    {
        try {
            RolEntity rolEntity = roleRepository.findById(UUID.fromString(id_rol)).orElseThrow(() -> new NotFoundException("Rol no encontrado"));

            if (rolEntity.getEliminado() == true) throw new BadRequestException("El rol fue eliminado");

            //Cambiando el estado de eliminado a true
            rolEntity.setEliminado(true);

            return mapper.map(roleRepository.save(rolEntity), RolDto.class);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
