package com.api.pasarela_dressy.services.Proveedor;

import com.api.pasarela_dressy.model.dto.Proveedor.CreateProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.ProveedorDto;
import com.api.pasarela_dressy.model.dto.Proveedor.UpdateProveedorDto;

import java.util.List;

public interface IProveedorService
{
    ProveedorDto create(CreateProveedorDto proveedorDto);
    List<ProveedorDto> getAll();
    ProveedorDto update(UpdateProveedorDto proveedorDto, String id_proveedor);
    ProveedorDto delete(String id_proveedor);
    ProveedorDto restore(String id_proveedor);
}
