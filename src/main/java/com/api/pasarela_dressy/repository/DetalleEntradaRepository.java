package com.api.pasarela_dressy.repository;


import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface DetalleEntradaRepository extends CrudRepository<DetalleEntradaEntity, UUID>
{
    @Query("Select de From DetalleEntradaEntity de where de.entrada.id_entrada = ?1 ")
    List<DetalleEntradaEntity> getByEntradaId(UUID id_entrada);
}
