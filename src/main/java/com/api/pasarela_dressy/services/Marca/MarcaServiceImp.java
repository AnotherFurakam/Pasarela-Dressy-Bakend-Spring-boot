package com.api.pasarela_dressy.services.Marca;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.repository.MarcaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImp implements IMarcaService
{
    @Autowired
    ModelMapper mapper;

    @Autowired
    MarcaRepository marcaRepository;

    @Override
    public List<MarcaDto> getAll()
    {
        //Obteniendo todas las marcas que no están eliminadas
        List<MarcaEntity> marcasFinded = marcaRepository.getAllUndeleted();

        //Convirtiendo la lista de entidades a una lista de dto's y retornandola
        return marcasFinded.stream().map(m -> mapper.map(m,MarcaDto.class)).collect(Collectors.toList());
    }

    @Override
    public MarcaDto create(CreateMarcaDto marcaDto)
    {
        //Capitalizando el nombre de la marca (primera letra en mayúscula)
        String capitalizedName = marcaDto.getNombre().substring(0,1).toUpperCase() + marcaDto.getNombre().substring(1);
        marcaDto.setNombre(capitalizedName);

        //Buscando si una marca con el mismo nombre ya existe
        MarcaEntity findedMarca = marcaRepository.getByNombre(capitalizedName);

        //Verificando si se encontró la marca y lanzando el error correspondiente si así es
        if (findedMarca != null)
            throw new BadRequestException("La marca con el nombre '"+capitalizedName+"' ya existe");

        //Mapeando los datos de la marca a registrar
        MarcaEntity marca = mapper.map(marcaDto, MarcaEntity.class);

        //Guardando la marca en la base de datos y retornando sus dotos
        return mapper.map(marcaRepository.save(marca),MarcaDto.class);
    }
}
