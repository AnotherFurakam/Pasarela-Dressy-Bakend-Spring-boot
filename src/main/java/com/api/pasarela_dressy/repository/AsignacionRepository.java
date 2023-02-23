package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.model.entity.RolEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AsignacionRepository extends CrudRepository<AsignacionEntity, UUID>
{

    @Query(value = "Select a From AsignacionEntity a Where a.empleado = ?1 and a.rol = ?2")
    AsignacionEntity getByEmpladoAndRol(EmpleadoEntity empleado, RolEntity rol);
}
