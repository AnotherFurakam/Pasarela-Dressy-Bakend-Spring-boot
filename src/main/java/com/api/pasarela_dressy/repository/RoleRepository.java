package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.RolEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<RolEntity, UUID> {
    @Query("Select r from RolEntity r where r.eliminado = 0")
    List<RolEntity> findAllUndeleted();
}
