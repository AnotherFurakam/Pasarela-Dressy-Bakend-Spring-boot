package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;
import com.api.pasarela_dressy.services.Categoria.ICategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CategoriaDto>> getAllCategoria()
    {
        return new ResponseEntity<>(categoriaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id_categoria}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable String id_categoria)
    {
        return new ResponseEntity<>(categoriaService.findById(id_categoria), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody CreateCategoriaDto categoriaDto)
    {
        return new ResponseEntity<>(categoriaService.create(categoriaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id_categoria}")
    public ResponseEntity<CategoriaDto> updateCategoria(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody UpdateCategoriaDto categoriaDto, @PathVariable String id_categoria)
    {
        return new ResponseEntity<>(categoriaService.update(categoriaDto, id_categoria), HttpStatus.OK);
    }

    @DeleteMapping("/{id_categoria}")
    public ResponseEntity<CategoriaDto> deleteCategoria(@PathVariable String id_categoria)
    {
        return new ResponseEntity<>(categoriaService.delete(id_categoria), HttpStatus.OK);
    }

    @PostMapping("/restore/{id_categoria}")
    public ResponseEntity<CategoriaDto> restoreCategoria(@PathVariable String id_categoria)
    {
        return new ResponseEntity<>(categoriaService.restore(id_categoria), HttpStatus.OK);
    }
}
