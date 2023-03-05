package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.TallaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TallaRepository extends CrudRepository<TallaEntity, UUID>
{
    @Query("Select t From TallaEntity t where t.eliminado = 0")
    List<TallaEntity> getAllUndeleted();

    @Query("Select t From TallaEntity t where t.nombre = ?1")
    TallaEntity getByName(String name);
}
