package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.model.entity.RolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AsignacionRepository extends CrudRepository<AsignacionEntity, UUID>
{

    @Query(value = "Select a From AsignacionEntity a Where a.empleado = ?1 and a.rol = ?2")
    AsignacionEntity getByEmpladoAndRol(EmpleadoEntity empleado, RolEntity rol);

    @Query("Select a from AsignacionEntity a where a.eliminado = 0 and a.rol = ?1 Order By a.creado_el ASC")
    Page<AsignacionEntity> getAllUndeletedWithPageable(RolEntity rol, Pageable pageable);

    @Query(value = "Select a From AsignacionEntity a Where a.eliminado = 0")
    List<AsignacionEntity> getAllUndelete();

    @Query(value = "Select a From AsignacionEntity a Where a.empleado = ?1")
    List<AsignacionEntity> getByEmpleado(EmpleadoEntity empleado);
}
