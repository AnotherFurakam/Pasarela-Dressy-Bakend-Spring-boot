package com.api.pasarela_dressy.services.Categoria;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.exception.UniqueFieldException;
import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;
import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.repository.CategoriaRepository;
import com.api.pasarela_dressy.utils.Capitalize;
import com.api.pasarela_dressy.utils.mappers.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriaServiceImp implements ICategoriaService
{
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    Capitalize capitalize;

    @Autowired
    CategoriaMapper categoriaMapper;

    //* Utils methods
    public CategoriaEntity getCategoriaById(String id_categoria)
    {
        try
        {
            return categoriaRepository.findById(UUID.fromString(id_categoria)).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        } catch (RuntimeException e)
        {
            throw new BadRequestException("El id ingresado no es válido");
        }

    }

    private void existDuplicateData(String name)
    {
        List<String> errors = new ArrayList<>();
        CategoriaEntity categoriaEntity = categoriaRepository.getByNombre(name);
        if (categoriaEntity != null)
        {
            errors.add("Ya existe la categoría con el nombre " + categoriaEntity.getNombre());
            throw new UniqueFieldException(errors);
        }
    }

    private void existDuplicateDataWhenUpdate(
        String name,
        UUID id_categoria
    )
    {
        List<String> errors = new ArrayList<>();
        CategoriaEntity existByName = categoriaRepository.findByNameAndId(name, id_categoria);
        if (existByName != null) {
            errors.add("Ya existe la categoría con el nombre " + name);
            throw new UniqueFieldException(errors);
        }
    }


    //* Service Methods
    @Override
    public CategoriaDto findById(String id_categoria)
    {
        CategoriaEntity categoria = this.getCategoriaById(id_categoria);

        return categoriaMapper.toDto(categoria);
    }

    @Override
    public CategoriaDto create(CreateCategoriaDto categoriaDto)
    {
        //* Capitalizando la primera letra del nombre y reemplazandola en el contexto
        String capitalizedName = capitalize.CapitalizeText(categoriaDto.getNombre());
        categoriaDto.setNombre(capitalizedName);

        this.existDuplicateData(capitalizedName);

        //Mapeando los datos del dto a la entidad
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);

        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public List<CategoriaDto> getAll()
    {
        List<CategoriaEntity> categorias = categoriaRepository.getAllUndeleted();

        return categoriaMapper.toListDto(categorias);
    }


    @Override
    public CategoriaDto update(
        UpdateCategoriaDto categoriaDto,
        String id_categoria
    )
    {
        String capitalizedName = capitalize.CapitalizeText(categoriaDto.getNombre());
        categoriaDto.setNombre(capitalizedName);

        CategoriaEntity categoria = this.getCategoriaById(id_categoria);

        if (categoria.getEliminado())
            throw new BadRequestException("La categoría " + categoria.getNombre() + " esta eliminada");

        this.existDuplicateDataWhenUpdate(capitalizedName, categoria.getId_categoria());

        categoriaMapper.updateFromDto(categoriaDto, categoria);

        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDto delete(String id_categoria)
    {
        CategoriaEntity categoria = this.getCategoriaById(id_categoria);

        if (categoria.getEliminado())
            throw new BadRequestException("La categoría " + categoria.getNombre() + " esta eliminada");

        categoria.setEliminado(true);

        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDto restore(String id_categoria)
    {
        CategoriaEntity categoria = this.getCategoriaById(id_categoria);

        if (!categoria.getEliminado())
            throw new BadRequestException("La categoría " + categoria.getNombre() + " no esta eliminada");

        categoria.setEliminado(false);

        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }
}
