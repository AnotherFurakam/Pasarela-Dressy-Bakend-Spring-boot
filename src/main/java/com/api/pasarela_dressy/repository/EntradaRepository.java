package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.EntradaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EntradaRepository extends CrudRepository<EntradaEntity, UUID>
{
}
