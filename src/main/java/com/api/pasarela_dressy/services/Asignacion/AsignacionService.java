package com.api.pasarela_dressy.services.Asignacion;

import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.CreateAsignacionDto;

public interface AsignacionService
{
    AsignacionDto getAll();
    AsignacionDto getById();
    AsignacionDto create(CreateAsignacionDto asignacion);
    AsignacionDto update(CreateAsignacionDto asignacion);
    AsignacionDto delete(String id_asignacion);
    AsignacionDto disable(String id_asignacion);
    AsignacionDto enable(String id_asignacion);
    AsignacionDto restore(String id_asignacion);
}
