package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.MarcaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MarcaRepository extends CrudRepository<MarcaEntity, UUID>
{
    @Query("Select e from MarcaEntity e where e.eliminado = 0")
    List<MarcaEntity> getAllUndeleted();

    @Query("Select e from MarcaEntity e where e.nombre = ?1")
    MarcaEntity getByNombre(String nombre);
}
