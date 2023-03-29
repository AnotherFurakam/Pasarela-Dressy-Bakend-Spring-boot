package com.api.pasarela_dressy.services.Empleado;

import com.api.pasarela_dressy.model.dto.Empleado.*;
import com.api.pasarela_dressy.model.dto.pagination.PaginationDto;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;

import java.util.List;

public interface EmpleadoService
{
    List<EmpleadoDto> getAll();
    PaginationDto<EmpleadoDto> getAllWithPagination(int pageNumber, int pageSize);
    PaginationDto<ShortEmpleadoDto> getAllNoAsignatedInSpecificRol(int pageNumber, int pageSize, String id_rol);
    EmpleadoDto getById(String id_empleado);
    EmpleadoDto create(CreateEmpleadoDto empleado);
    EmpleadoDto update(UpdateEmpleadoDto empleado, String id_empleado);
    EmpleadoDto delete(String id_empleado);
    EmpleadoDto restore(String id_empleado);
    EmpleadoDto disable(String id_empleado);
    EmpleadoDto enable(String id_empleado);
    EmpleadoDto changePassword(String id_empleado, ChangePasswordDto passwordDto);
}
