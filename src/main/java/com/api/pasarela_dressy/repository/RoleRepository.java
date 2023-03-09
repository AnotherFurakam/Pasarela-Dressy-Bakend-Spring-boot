package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.RolEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<RolEntity, UUID> {
    @Query("Select r from RolEntity r where r.eliminado = 0")
    List<RolEntity> findAllUndeleted();

    @Query("Select r from RolEntity r where r.nombre = ?1")
    RolEntity findByRoleName(String name);

    @Query("Select r from RolEntity r where r.nombre = ?1 and r.id_rol != ?2")
    RolEntity findByRoleNameAndId(String name, UUID id_rol);
}
