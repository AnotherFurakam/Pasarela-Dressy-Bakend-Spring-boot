package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<CategoriaEntity,UUID>
{
    @Query("Select c from CategoriaEntity c where c.eliminado = 0")
    List<CategoriaEntity> getAllUndeleted();

    @Query("Select c from CategoriaEntity c where c.nombre = ?1")
    CategoriaEntity getByNombre(String nombre);

    @Query("Select c from CategoriaEntity c where c.nombre = ?1 and c.id_categoria != ?2")
    CategoriaEntity findByNameAndId(String name, UUID id_categoria);
}
