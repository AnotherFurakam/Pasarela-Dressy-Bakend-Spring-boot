package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.model.entity.ProductoTallaEntity;
import com.api.pasarela_dressy.model.entity.TallaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductoTallaRepository extends CrudRepository<ProductoTallaEntity, UUID>
{

    @Query("Select pt From ProductoTallaEntity pt where pt.producto = ?1 and pt.talla = ?2")
    ProductoTallaEntity getByProductoAndTalla(ProductoEntity producto, TallaEntity talla);
}
