package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;
import com.api.pasarela_dressy.services.Categoria.ICategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categoria")
@Tag(name = "Categoria")
public class CategoriaController
{
    @Autowired
    ICategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDto> getAllCategoria()
    {
        return categoriaService.getAll();
    }

    @GetMapping("/{id_categoria}")
    public CategoriaDto getCategoriaById(@PathVariable String id_categoria)
    {
        return categoriaService.findById(id_categoria);
    }

    @PostMapping
    public CategoriaDto createCategoria(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody CreateCategoriaDto categoriaDto)
    {
        return categoriaService.create(categoriaDto);
    }

    @PutMapping("/{id_categoria}")
    public CategoriaDto updateCategoria(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody UpdateCategoriaDto categoriaDto, @PathVariable String id_categoria)
    {
        return categoriaService.update(categoriaDto, id_categoria);
    }

    @DeleteMapping("/{id_categoria}")
    public CategoriaDto deleteCategoria(@PathVariable String id_categoria)
    {
        return categoriaService.delete(id_categoria);
    }

    @PostMapping("/restore/{id_categoria}")
    public CategoriaDto restoreCategoria(@PathVariable String id_categoria)
    {
        return categoriaService.restore(id_categoria);
    }
}
