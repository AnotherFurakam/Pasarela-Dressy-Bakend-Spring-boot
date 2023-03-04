package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.ImagenEntity;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ImagenRepository extends CrudRepository<ImagenEntity, UUID>
{
    @Query("Select e from ImagenEntity e where e.eliminado = 0")
    List<ImagenEntity> getAllUndeleted();
}
