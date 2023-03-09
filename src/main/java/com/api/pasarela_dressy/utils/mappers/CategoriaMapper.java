package com.api.pasarela_dressy.utils.mappers;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.model.dto.Categoria.CategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.CreateCategoriaDto;
import com.api.pasarela_dressy.model.dto.Categoria.UpdateCategoriaDto;
import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaMapper
{
    @Autowired
    ModelMapper mapper;

    /**
     * Convierte la entidad de categoria al dto de categoria mapeando sus datos en ella
     * @param categoriaEntity Objeto de tipo CategoriaEntity
     * @return Objeto de tipo CategoriaDto con los datos mapeados
     */
    public CategoriaDto toDto(CategoriaEntity categoriaEntity)
    {
        return mapper.map(categoriaEntity, CategoriaDto.class);
    }

    /**
     * Mapea los datos de CreateCategoriaDto o UpdateCategoriaDto al objeto CategoriaEntity
     * @param categoriaDto permite cualquier objeto pero si no es de tio UpdateCategoriaDto o CreateCategoriaDto da un error
     * @return objeto de tipo CategoriaEntity con los datos del dto
     */
    public CategoriaEntity toEntity(Object categoriaDto)
    {
        if (categoriaDto instanceof CreateCategoriaDto)
            return mapper.map(categoriaDto, CategoriaEntity.class);
        throw new BadRequestException("No se puede mapear los datos de un objeto que no sea CreateCategoriaDto o UpdateCategoriaDto");
    }

    /**
     * Convirte una lista de entidades de categoría en una lista de dto's de categoría
     * * Mapeando los datos de cada entidad
     * @param categoriaEntityList Lista de tipo CategoriaEntity
     * @return La lista de entidades convertida a una lista de Dto's
     */
    public List<CategoriaDto> toListDto(List<CategoriaEntity> categoriaEntityList)
    {
        return categoriaEntityList.stream().map(ce -> mapper.map(ce, CategoriaDto.class)).collect(Collectors.toList());
    }

    public void updateFromDto(UpdateCategoriaDto updateCategoriaDto, CategoriaEntity categoriaEntity)
    {
        mapper.map(updateCategoriaDto, categoriaEntity);
    }
}
