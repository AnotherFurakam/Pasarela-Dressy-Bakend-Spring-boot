package com.api.pasarela_dressy.services.Entrada;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Entrada.CreateEntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaWithDetailsDto;
import com.api.pasarela_dressy.model.entity.DetalleEntradaEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.model.entity.EntradaEntity;
import com.api.pasarela_dressy.model.entity.ProveedorEntity;
import com.api.pasarela_dressy.repository.DetalleEntradaRepository;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import com.api.pasarela_dressy.repository.EntradaRepository;
import com.api.pasarela_dressy.repository.ProveedorRepository;
import com.api.pasarela_dressy.utils.mappers.EntradaMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EntradaServiceImpl implements IEntradaService
{
    @Autowired
    EntradaRepository entradaRepository;

    @Autowired
    DetalleEntradaRepository detalleEntradaRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    EntradaMapper entradaMapper;

    @Override
    public EntradaDto create(CreateEntradaDto entradaDto)
    {
        try
        {
            ProveedorEntity proveedor = proveedorRepository.findById(UUID.fromString(entradaDto.getId_proveedor())).orElseThrow(() -> new NotFoundException("El proveedor no fue encontrado"));
            if (proveedor.getEliminado())
                throw new BadRequestException("El proveedor fue eliminado");

            EmpleadoEntity empleado = empleadoRepository.findById(UUID.fromString(entradaDto.getId_empleado())).orElseThrow(() -> new NotFoundException("El empleado no fue encontrado"));
            if (empleado.getEliminado())
                throw new BadRequestException("El empleado fue eliminado");

            EntradaEntity entrada = entradaMapper.createtoEntity(entradaDto);
            entrada.setProveedor(proveedor);
            entrada.setEmpleado(empleado);

            return entradaMapper.toDto(entradaRepository.save(entrada));

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EntradaWithDetailsDto getById(String id_entrada)
    {
        try
        {
            List<DetalleEntradaEntity> detalleEntradaEntityList = detalleEntradaRepository.getByEntradaId(UUID.fromString(id_entrada));
            EntradaEntity entrada = entradaRepository.findById(UUID.fromString(id_entrada)).orElseThrow(() -> new NotFoundException("La entrada no fue encontrada"));
            return entradaMapper.toEntradaWithDetailDto(entrada, detalleEntradaEntityList);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
