package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.ShortEmpleadoDto;
import com.api.pasarela_dressy.model.dto.Empleado.UpdateEmpleadoDto;
import com.api.pasarela_dressy.model.dto.asignacion.ShortAsignacionDto;
import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.AsignacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * @param empleadoEntity entidad de empleado a convertir
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

    public ShortEmpleadoDto toShortDto(EmpleadoEntity empleadoEntity) {
        return mapper.map(empleadoEntity, ShortEmpleadoDto.class);
    }

    /**
     *
     * @param empleadoDto dto del empleado a convertir puede ser el dto de Create o Update
     * @return entidad de empleado con la información del dto
     */
    public EmpleadoEntity toEntity(Object empleadoDto)
    {
        return mapper.map(empleadoDto, EmpleadoEntity.class);
    }

    /**
     * Mapea los datos de una Lista de EmpleadoEntity a una lista de EmpleadoDto hacciendo uso de la funcion toDto
     * para el mapeado individual de cada entidad
     *
     * @param empleados lista de entidades de empleados
     * @return lista de dto de empleados
     */
    public List<EmpleadoDto> toListDto(List<EmpleadoEntity> empleados)
    {
        return empleados.stream().map(this::toDto).toList();
    }

    /**
     * Mapea los datos de una Lista de EmpleadoEntity a una lista de EmpleadoDto hacciendo uso de la funcion toDto
     * para el mapeado individual de cada entidad
     *
     * @param empleados lista de entidades de empleados
     * @return lista de dto de empleados
     */
    public List<ShortEmpleadoDto> toListShortDto(List<EmpleadoEntity> empleados)
    {
        return empleados.stream().map(this::toShortDto).toList();
    }

    /**
     * Este método actualiza los datos de la entidad por los del dto de entrada
     * @param empleadoDto dto que contiene la información actualizada del empleado
     * @param empleadoEntity entidad del empleado en la que mapearemos la información actualizada
     */
    public void updateEmpleadoFromDto(UpdateEmpleadoDto empleadoDto, EmpleadoEntity empleadoEntity)
    {
        mapper.map(empleadoDto, empleadoEntity);
    }
}
