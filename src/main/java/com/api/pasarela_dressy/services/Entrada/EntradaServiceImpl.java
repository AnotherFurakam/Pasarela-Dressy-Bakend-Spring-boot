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
import com.api.pasarela_dressy.repository.EntradaRepository;
import com.api.pasarela_dressy.services.Empleado.EmpleadoServiceImp;
import com.api.pasarela_dressy.services.ProductoTalla.IProductoTallaService;
import com.api.pasarela_dressy.services.Proveedor.ProveedorServiceImp;
import com.api.pasarela_dressy.utils.mappers.EntradaMapper;
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
    EntradaMapper entradaMapper;

    @Autowired
    ProveedorServiceImp proveedorServiceImp;

    @Autowired
    EmpleadoServiceImp empleadoServiceImp;

    @Autowired
    IProductoTallaService productoTallaService;


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

    public List<DetalleEntradaEntity> findDetalleEntradaListByEntradaId(String id_entrada)
    {
        try
        {
            return detalleEntradaRepository.getByEntradaId(UUID.fromString(id_entrada));
        } catch (RuntimeException e)
        {
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
        List<DetalleEntradaEntity> detalleEntradaEntityList = this.findDetalleEntradaListByEntradaId(id_entrada);
        EntradaEntity entrada = this.findEntradaById(id_entrada);
        return entradaMapper.toEntradaWithDetailDto(entrada, detalleEntradaEntityList);
    }

    @Override
    public EntradaWithDetailsDto executeEntrada(String id_entrada)
    {
        EntradaEntity entradaEntity = this.findEntradaById(id_entrada);
        if (entradaEntity.getEjecutado()) throw new BadRequestException("La entrada ya fue ejecutada");

        List<DetalleEntradaEntity> detalleEntradaEntityList = this.findDetalleEntradaListByEntradaId(id_entrada);
        if (detalleEntradaRepository.count() <= 0)
            throw new BadRequestException("La entrada debe tener como mínimo un detalle de entrada");

        detalleEntradaEntityList.forEach(de -> {
            productoTallaService.create(de);
        });

        entradaEntity.setEjecutado(true);

        return entradaMapper.toEntradaWithDetailDto(entradaRepository.save(entradaEntity), detalleEntradaEntityList);
    }
}
