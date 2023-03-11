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
import com.api.pasarela_dressy.services.Empleado.EmpleadoServiceImp;
import com.api.pasarela_dressy.services.Proveedor.ProveedorServiceImp;
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

    @Autowired
    ProveedorServiceImp proveedorServiceImp;

    @Autowired
    EmpleadoServiceImp empleadoServiceImp;


    //* Utils method
    public EntradaEntity findEntradaById(String id_entrada)
    {
        try
        {
            return entradaRepository.findById(UUID.fromString(id_entrada)).orElseThrow(() -> new NotFoundException("La entrada no fue encontrada"));
        } catch (RuntimeException e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }


    //* Service Methods
    @Override
    public EntradaDto create(CreateEntradaDto entradaDto)
    {
        ProveedorEntity proveedor = proveedorServiceImp.getProveedorById(entradaDto.getId_proveedor());
        if (proveedor.getEliminado()) throw new BadRequestException("El proveedor fue eliminado");

        EmpleadoEntity empleado = empleadoServiceImp.getEmpleadoById(entradaDto.getId_empleado());
        if (empleado.getEliminado()) throw new BadRequestException("El empleado fue eliminado");

        EntradaEntity entrada = entradaMapper.createtoEntity(entradaDto);
        entrada.setProveedor(proveedor);
        entrada.setEmpleado(empleado);
        return entradaMapper.toDto(entradaRepository.save(entrada));
    }

    @Override
    public EntradaWithDetailsDto getById(String id_entrada)
    {
        List<DetalleEntradaEntity> detalleEntradaEntityList = detalleEntradaRepository.getByEntradaId(UUID.fromString(id_entrada));
        EntradaEntity entrada = this.findEntradaById(id_entrada);
        return entradaMapper.toEntradaWithDetailDto(entrada, detalleEntradaEntityList);
    }
}
