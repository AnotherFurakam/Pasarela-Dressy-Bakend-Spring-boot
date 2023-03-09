package com.api.pasarela_dressy.services.Role;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Rol.CrearRolDto;
import com.api.pasarela_dressy.model.dto.Rol.RolDto;
import com.api.pasarela_dressy.model.dto.Rol.UpdateRolDto;
import com.api.pasarela_dressy.model.entity.RolEntity;
import com.api.pasarela_dressy.repository.RoleRepository;
import com.api.pasarela_dressy.utils.mappers.RolMapper;
import jakarta.validation.ConstraintDeclarationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImp implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RolMapper rolMapper;

    //* Utils methods

    /**
     * Este método retorna la información del rol utilizando su id para buscarla en la base de datos
     * @param id_rol id de del rol de tipo String
     * @return el dto del rol con la información solicitada
     */
    public RolEntity getRolById(String id_rol)
    {
        try
        {
            RolEntity rolEntity = roleRepository.findById(UUID.fromString(id_rol)).orElseThrow(() -> new NotFoundException("Rol no encontrado"));
            return rolEntity;
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Verifica que no existan datos duplicados en la base de dtos (nombre de rol)<br>
     ** Verifica el nombre de todas las entidades de la tabla. <br>
     * En caso de encontrar coincidencias lanza un error indicando los campos con datos duplicados
     * @param name nombre del rol a verificar
     */
    private void existDuplicateData(String name)
    {
        List<String> errors = new ArrayList<>();

        RolEntity rolByName = roleRepository.findByRoleName(name);

        if (rolByName != null)
        {
            errors.add("Ya existe un rol con el nombre " + name);
            throw new UniqueFieldException(errors);
        }
    }

    /**
     * Verifica si existen entidades con el mismo rol <br>
     ** Busca coincidencias en todas las entidades menos ela entidad cuyo id le pasemos.
     ** Es decir que si que el dato solo se puede repetir en esta entidad.
     * En caso de existir coincidencias lanza un error indicando los campos donde se encontraros coincidencias
     * @param name nombre del rol a verificar
     * @param id_rol id del rol que se va a excluir de la verificación
     */
    private void existDuplicateDataWhenUpdate(
        String name,
        UUID id_rol
    )
    {
        List<String> errors = new ArrayList<>();

        RolEntity rolByNameAndId = roleRepository.findByRoleNameAndId(name, id_rol);

        if (rolByNameAndId != null)
        {
            errors.add("Ya existe un rol con le nombre " + name);
            throw new UniqueFieldException(errors);
        }
    }


    // * Service method
    @Override
    public List<RolDto> getAll()
    {
        List<RolEntity> rolesEntities = (List<RolEntity>) roleRepository.findAllUndeleted();

        return rolMapper.toListDto(rolesEntities);
    }

    @Override
    public RolDto getById(String id_rol)
    {
        RolEntity rolEntity = this.getRolById(id_rol);

        if (rolEntity.getEliminado() == true) throw new BadRequestException("El rol fue eliminado");

        return rolMapper.toDto(rolEntity);
    }

    @Override
    public RolDto createRole(CrearRolDto rol)
    {
        RolEntity rolEntity = rolMapper.toEntity(rol);
        this.existDuplicateData(rolEntity.getNombre());

        return rolMapper.toDto(roleRepository.save(rolEntity));
    }

    @Override
    public RolDto updateRole(
        UpdateRolDto rolDto,
        String id_rol
    )
    {
        RolEntity rolEntity = getRolById(id_rol);

        if (rolEntity.getEliminado() == true) throw new BadRequestException("El rol fue eliminado");

        this.existDuplicateDataWhenUpdate(rolDto.getNombre(), rolEntity.getId_rol());

        rolMapper.updateEntityFromDto(rolDto, rolEntity);

        return rolMapper.toDto(roleRepository.save(rolEntity));
    }

    @Override
    public RolDto deleteRole(String id_rol)
    {
        RolEntity rolEntity = this.getRolById(id_rol);

        if (rolEntity.getEliminado()) throw new BadRequestException("El rol ya fue eliminado");

        rolEntity.setEliminado(true);

        return rolMapper.toDto(roleRepository.save(rolEntity));
    }

    @Override
    public RolDto restoreRole(String id_rol)
    {
        RolEntity rolEntity = this.getRolById(id_rol);

        if (!rolEntity.getEliminado()) throw new BadRequestException("El rol no esta eliminado");

        rolEntity.setEliminado(false);

        return rolMapper.toDto(roleRepository.save(rolEntity));
    }
}
