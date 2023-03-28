package com.api.pasarela_dressy.utils;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.model.dto.Empleado.EmpleadoDto;
import com.api.pasarela_dressy.model.dto.pagination.PaginationDto;
import com.api.pasarela_dressy.utils.mappers.EmpleadoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T>
{
    Page<T> pageResult;

    /**
     * Retorna la lista de datos de la página
     * @return List<T>
     */
    public List<T> getPageData () {
        return pageResult.getContent();
    }

    /**
     * Retorna el número de página anterior, en caso de no existir retorna null
     * @return Integer
     */
    public Integer getPrevPageNumber()
    {
        if (this.getPageResult().hasPrevious())
        {
            return pageResult.previousPageable().getPageNumber() + 1;
        } else
        {
            return null;
        }
    }

    /**
     * Retorna el número de la siguiente página, en caso de no existir retorna null
     * @return Ineteger
     */
    public Integer getNextpageNumber()
    {
        if (this.getPageResult().hasNext())
        {
            return pageResult.nextPageable().getPageNumber() + 1;
        } else
        {
            return null;
        }
    }

    /**
     * Retorna el número total de páginas que existen. <br>
     * Si la página no solicitada es mayor al número total de páginas
     * se lanzara una exepción BadRequestExepction que dira que la página solicitada
     * no existe
     * @param pageNumber int
     * @return int
     */
    public int getTotalPageNumber(int pageNumber)
    {
        int totalPage =  this.pageResult.getTotalPages();
        if (pageNumber <= totalPage){
            return this.pageResult.getTotalPages();
        } else {
            throw new BadRequestException("La página solicitada no existe");
        }
    }

}
