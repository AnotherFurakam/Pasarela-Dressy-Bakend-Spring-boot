package com.api.pasarela_dressy.model.dto.pagination;

import java.util.List;

public record PaginationDto<T>(
    List<T> data,
    int pageNumber,
    int pageSize,
    int totalPages,
    Integer prevPage,
    Integer nextPage
)
{
}
