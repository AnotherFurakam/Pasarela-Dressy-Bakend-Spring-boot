package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<CategoriaEntity,UUID>
{
    @Query("Select e from CategoriaEntity e where e.eliminado = 0")
    List<CategoriaEntity> getAllUndeleted();

    @Query("Select e from CategoriaEntity e where e.nombre = ?1")
    CategoriaEntity getByNombre(String nombre);
}
