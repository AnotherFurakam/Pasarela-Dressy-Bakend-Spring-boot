package com.api.pasarela_dressy.services.Asignacion;

import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.CreateAsignacionDto;

import java.util.List;

public interface AsignacionService
{
    List<AsignacionDto> getAll();
    AsignacionDto getById(String asignacion);
    AsignacionDto create(CreateAsignacionDto asignacion);
    AsignacionDto delete(String id_asignacion);
}
