package com.api.pasarela_dressy.utils;

import com.api.pasarela_dressy.model.entity.CategoriaEntity;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.model.entity.ProductoEntity;
import com.api.pasarela_dressy.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SkuGenerate
{
    @Autowired
    ProductoRepository productoRepository;

    public String createSkuCode(ProductoEntity producto, MarcaEntity marca, CategoriaEntity categoria)
    {
        String productoCod = producto.getNombre().substring(0, 3);
        String marcaCod = marca.getNombre().substring(0, 3);
        String categoriaCod = categoria.getNombre().substring(0, 3);
        String sku = productoCod + "-" + marcaCod + "-" + categoriaCod + "-" + "1";
        while (true)
        {
            ProductoEntity prodFindBySku = productoRepository.getBySku(sku);
            if (prodFindBySku == null) break;
            String[] skuPart = sku.split("-");
            sku = skuPart[0]+ "-" + skuPart[1] + "-" + skuPart[2] + "-" + (Integer.parseInt(skuPart[3]) + 1);
        }
        return sku;
    }
}
