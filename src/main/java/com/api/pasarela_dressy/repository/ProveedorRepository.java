package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProveedorRepository extends CrudRepository<ProveedorEntity, UUID>
{
    @Query("Select p From ProveedorEntity p Where p.eliminado = 0")
    List<ProveedorEntity> findAllUndeleted();

    @Query("Select p from ProveedorEntity p where p.eliminado = 0 Order By p.creado_el ASC")
    Page<ProveedorEntity> getAllUndeletedWithPageable(Pageable pageable);

    @Query("Select p From ProveedorEntity p Where p.nombre = ?1")
    ProveedorEntity findByName(String nombre);

    @Query("Select p From ProveedorEntity p Where p.nombre = ?1 and p.id_proveedor != ?2")
    ProveedorEntity findByNameButExcludeById(String nombre, UUID id_proveedor);
}
