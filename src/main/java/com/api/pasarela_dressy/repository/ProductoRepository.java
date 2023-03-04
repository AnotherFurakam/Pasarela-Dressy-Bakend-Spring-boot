package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductoRepository extends CrudRepository<ProductoEntity, UUID>
{
    @Query("Select e from ProductoEntity e where e.eliminado = 0")
    List<ProductoEntity> getAllUndeleted();

    @Query("Select e from ProductoEntity e where e.sku = ?1")
    ProductoEntity getBySku(String sku);
}
