package com.api.pasarela_dressy.services.Marca;

import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;

import java.util.List;

public interface IMarcaService
{
    List<MarcaDto> getAll();
    MarcaDto create(CreateMarcaDto marcaDto);
}
