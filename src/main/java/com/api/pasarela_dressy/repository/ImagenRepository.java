package com.api.pasarela_dressy.repository;

import com.api.pasarela_dressy.model.entity.ImagenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ImagenRepository extends CrudRepository<ImagenEntity, UUID>
{
    @Query("Select i from ImagenEntity i where i.url = ?1")
    ImagenEntity getImagenByUrl(String url);

    @Query("Select i from ImagenEntity i where i.producto.id_producto = ?1 and i.eliminado = 0")
    List<ImagenEntity> getByIdProducto(UUID id_producto);
}
