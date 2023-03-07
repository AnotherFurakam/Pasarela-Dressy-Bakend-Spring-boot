package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.model.dto.Empleado.CreateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.asignacion.AsignacionDto;
import com.api.pasarela_dressy.model.dto.asignacion.ShortAsignacionDto;
import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.AsignacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmpleadoMapper
{
    @Autowired
    ModelMapper mapper;

    @Autowired
    AsignacionRepository asignacionRepository;

    /**
     * Mapea los datos de una clase de tipo EmpleadoEntity a EmpleadoDto
     *
     * @param empleadoEntity
     * @return EmpleadoDto
     */
    public EmpleadoDto toDto(EmpleadoEntity empleadoEntity)
    {
        EmpleadoDto dto = mapper.map(empleadoEntity, EmpleadoDto.class);

        //Obteniendo los roles del usuario
        List<AsignacionEntity> asignaciones = asignacionRepository.getByEmpleado(empleadoEntity);
        List<ShortAsignacionDto> shortAsignacionDtoList = asignaciones.stream().map(a -> mapper.map(a, ShortAsignacionDto.class)).toList();

        //Asignando los roles encontrados al dto de respuesta
        dto.setRoles(shortAsignacionDtoList);

        return dto;
    }

    /**
     *
     * @param empleadoDto
     * @return
     */
    public EmpleadoEntity toEntity(Object empleadoDto)
    {
        return mapper.map(empleadoDto, EmpleadoEntity.class);
    }

    /**
     * Mapea los datos de una Lista de EmpleadoEntity a una lista de EmpleadoDto hacciendo uso de la funcion toDto
     * para el mapeado individual de cada entidad
     *
     * @param empleados
     * @return
     */
    public List<EmpleadoDto> toListDto(List<EmpleadoEntity> empleados)
    {
        return empleados.stream().map(this::toDto).toList();
    }

    /**
     * Este método actualiza los datos de la entidad por los del dto de entrada
     * @param empleadoDto
     * @param empleadoEntity
     */
    public void updateEntity(UpdateEmpleadoDto empleadoDto, EmpleadoEntity empleadoEntity)
    {
        mapper.map(empleadoDto, empleadoEntity);
    }
}
