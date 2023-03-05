package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProveedorRepository extends CrudRepository<ProveedorEntity, UUID>
{
    @Query("Select p From ProveedorEntity p Where p.eliminado = 0")
    List<ProveedorEntity> findAllUndeleted();

    @Query("Select p From ProveedorEntity p Where p.nombre = ?1")
    ProveedorEntity findByName(String nombre);
}
