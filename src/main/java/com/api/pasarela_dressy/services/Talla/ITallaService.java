package com.api.pasarela_dressy.services.Talla;

import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;

import java.util.List;

public interface ITallaService
{
    TallaDto create(CreateTallaDto tallaDto);

    TallaDto update(UpdateTallaDto tallaDto, String id_talla);

    List<TallaDto> getAll();

    TallaDto delete(String id_talla);

    TallaDto restore(String id_talla);

}
