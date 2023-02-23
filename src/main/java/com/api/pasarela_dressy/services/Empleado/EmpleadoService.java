package com.api.pasarela_dressy.services.Empleado;

import com.api.pasarela_dressy.model.dto.Empleado.CreateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService
{
    List<EmpleadoDto> getAll();
    EmpleadoDto getById(String id_empleado);
    EmpleadoDto create(CreateEmpleadoDto empleado);
    EmpleadoDto update(UpdateEmpleadoDto empleado, String id_empleado);
    EmpleadoDto delete(String id_empleado);
    EmpleadoDto restore(String id_empleado);
    EmpleadoDto disable(String id_empleado);
    EmpleadoDto enable(String id_empleado);
}
