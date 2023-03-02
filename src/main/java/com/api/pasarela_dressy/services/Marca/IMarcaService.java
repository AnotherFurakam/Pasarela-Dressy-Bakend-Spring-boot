package com.api.pasarela_dressy.services.Marca;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;

import java.util.List;

public interface IMarcaService
{
    List<MarcaDto> getAll();
    MarcaDto create(CreateMarcaDto marcaDto);
    MarcaDto update(UpdateMarcaDto marcaDto, String id_marca);
    MarcaDto delete(String id_marca);
    MarcaDto restore(String id_marca);
}
