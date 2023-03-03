package com.api.pasarela_dressy.services.Categoria;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;
import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.repository.CategoriaRepository;
import com.api.pasarela_dressy.utils.Capitalize;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImp implements ICategoriaService
{
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Capitalize capitalize;

    @Override
    public CategoriaDto create(CreateCategoriaDto categoriaDto)
    {
        //Capitalizando la primera letra del nombre y reemplazandola en el contexto
        String capitalizedName = capitalize.CapitalizeText(categoriaDto.getNombre());
        categoriaDto.setNombre(capitalizedName);

        //Verificando si existe una categoría registrada con el nombre
        CategoriaEntity findCategoria = categoriaRepository.getByNombre(capitalizedName);
        if (findCategoria != null)
            throw new BadRequestException("La categoría con el nombre '" + findCategoria.getNombre() + "' ya existe");

        //Mapeando los datos del dto a la entidad
        CategoriaEntity categoria = mapper.map(categoriaDto, CategoriaEntity.class);

        return mapper.map(categoriaRepository.save(categoria), CategoriaDto.class);
    }

    @Override
    public List<CategoriaDto> getAll()
    {
        List<CategoriaEntity> categorias = categoriaRepository.getAllUndeleted();

        return categorias.stream().map(c -> mapper.map(c, CategoriaDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoriaDto findById(String id_categoria)
    {
        try
        {
            CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(id_categoria)).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

            return mapper.map(categoria, CategoriaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CategoriaDto update(UpdateCategoriaDto categoriaDto, String id_categoria)
    {
        try
        {
            //Capitalizando la primera letra del nombre de la categoria en el dto
            String capitalizedName = capitalize.CapitalizeText(categoriaDto.getNombre());
            categoriaDto.setNombre(capitalizedName);

            //Obteniendo la categoría mediante su id
            CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(id_categoria)).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

            //Verificando si la categoría esta eliminada
            if (categoria.getEliminado())
                throw new BadRequestException("La categoría " + categoria.getNombre() + " esta eliminada");

            if (!categoria.getNombre().equals(capitalizedName))
            {
                //Verificando si existe otra categoría con el nombre
                CategoriaEntity categoriaByName = categoriaRepository.getByNombre(capitalizedName);
                if (categoriaByName != null)
                    throw new BadRequestException("La categoría con el nombre '" + capitalizedName + "' ya existe");

                //Actualizando datos de la endida categoría a editar
                mapper.map(categoriaDto, categoria);

                //Guardando cambios y retornando
                return mapper.map(categoriaRepository.save(categoria), CategoriaDto.class);
            }

            //En caso de que sea el mismo nombre retornara los datos de la categoría encontrada sin actualizar
            return mapper.map(categoria, CategoriaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CategoriaDto delete(String id_categoria)
    {
        try
        {
            //Obteniendo la categoría mediante su id
            CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(id_categoria)).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

            //Verificando si la categoría esta eliminada
            if (categoria.getEliminado())
                throw new BadRequestException("La categoría " + categoria.getNombre() + " esta eliminada");

            categoria.setEliminado(true);

            //En caso de que sea el mismo nombre retornara los datos de la categoría encontrada sin actualizar
            return mapper.map(categoriaRepository.save(categoria), CategoriaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CategoriaDto restore(String id_categoria)
    {
        try
        {
            //Obteniendo la categoría mediante su id
            CategoriaEntity categoria = categoriaRepository.findById(UUID.fromString(id_categoria)).orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

            //Verificando si la categoría esta eliminada
            if (!categoria.getEliminado())
                throw new BadRequestException("La categoría " + categoria.getNombre() + " no esta eliminada");

            categoria.setEliminado(false);

            //En caso de que sea el mismo nombre retornara los datos de la categoría encontrada sin actualizar
            return mapper.map(categoriaRepository.save(categoria), CategoriaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
