package com.api.pasarela_dressy.services.Categoria;

import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;

import java.util.List;

public interface ICategoriaService
{
    CategoriaDto create(CreateCategoriaDto categoriaDto);
    List<CategoriaDto> getAll();
    CategoriaDto findById(String id_categoria);
    CategoriaDto update(UpdateCategoriaDto categoriaDto, String id_categoria);
    CategoriaDto delete(String id_categoria);
    CategoriaDto restore(String id_categoria);
}
