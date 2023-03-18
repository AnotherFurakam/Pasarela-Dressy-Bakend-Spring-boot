package com.api.pasarela_dressy.services.Entrada;

import com.api.pasarela_dressy.model.dto.Entrada.CreateEntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaDto;
import com.api.pasarela_dressy.model.dto.Entrada.EntradaWithDetailsDto;

public interface IEntradaService
{
    EntradaDto create(CreateEntradaDto entradaDto);

    EntradaWithDetailsDto getById(String id_entrada);

    EntradaWithDetailsDto executeEntrada(String id_entrada);

}
