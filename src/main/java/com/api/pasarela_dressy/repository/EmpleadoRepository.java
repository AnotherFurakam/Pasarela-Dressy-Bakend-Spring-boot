package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EmpleadoRepository extends CrudRepository<EmpleadoEntity, UUID>
{
    @Query("Select e from EmpleadoEntity e where e.eliminado = 0 Order By e.creado_el ASC")
    List<EmpleadoEntity> getAllUndeleted();


    @Query("Select e from EmpleadoEntity e where e.eliminado = 0 Order By e.creado_el ASC")
    Page<EmpleadoEntity> getAllUndeletedWithPageable(Pageable pageable);

    @Query("Select e from EmpleadoEntity e where e.dni = ?1")
    EmpleadoEntity getByDni(String dni);

    @Query("Select e from EmpleadoEntity e where e.correo = ?1")
    EmpleadoEntity getByCorreo(String email);

    @Query("Select e from EmpleadoEntity e where e.dni = ?1 and e.id_empleado != ?2")
    EmpleadoEntity getByDniAndId(String dni, UUID id_empleado);

    @Query("Select e from EmpleadoEntity e where e.correo = ?1 and e.id_empleado != ?2")
    EmpleadoEntity getByCorreoAndId(String correo, UUID id_empleado);
}
