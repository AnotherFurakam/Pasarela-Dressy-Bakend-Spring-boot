package com.api.pasarela_dressy.restcontroller;

import com.api.pasarela_dressy.model.dto.Talla.CreateTallaDto;
import com.api.pasarela_dressy.model.dto.Talla.TallaDto;
import com.api.pasarela_dressy.model.dto.Talla.UpdateTallaDto;
import com.api.pasarela_dressy.services.Talla.ITallaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talla")
@Tag(name = "Talla")
public class TallaController
{
    @Autowired
    ITallaService tallaService;

    @GetMapping
    public List<TallaDto> getAllTallas(){
        return tallaService.getAll();
    }

    @PostMapping
    public TallaDto createTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody CreateTallaDto createTallaDto)
    {
        return tallaService.create(createTallaDto);
    }

    @PutMapping("/{id_talla}")
    public TallaDto updateTalla(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody UpdateTallaDto updateTallaDto, @PathVariable String id_talla)
    {
        return tallaService.update(updateTallaDto, id_talla);
    }

    @DeleteMapping("/{id_talla}")
    public TallaDto deleteTalla(String id_talla)
    {
        return tallaService.delete(id_talla);
    }


    @PostMapping("/restore/{id_talla}")
    public TallaDto restoreTalla(String id_talla)
    {
        return tallaService.restore(id_talla);
    }
}
