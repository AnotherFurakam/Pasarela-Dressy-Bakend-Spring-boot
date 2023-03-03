package com.api.pasarela_dressy.services.Marca;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.exception.NotFoundException;
import com.api.pasarela_dressy.model.dto.Marca.CreateMarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.MarcaDto;
import com.api.pasarela_dressy.model.dto.Marca.UpdateMarcaDto;
import com.api.pasarela_dressy.model.entity.MarcaEntity;
import com.api.pasarela_dressy.repository.MarcaRepository;
import com.api.pasarela_dressy.utils.Capitalize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImp implements IMarcaService
{
    @Autowired
    ModelMapper mapper;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    Capitalize capitalize;


    @Override
    public List<MarcaDto> getAll()
    {
        //Obteniendo todas las marcas que no están eliminadas
        List<MarcaEntity> marcasFinded = marcaRepository.getAllUndeleted();

        //Convirtiendo la lista de entidades a una lista de dto's y retornandola
        return marcasFinded.stream().map(m -> mapper.map(m, MarcaDto.class)).collect(Collectors.toList());
    }

    @Override
    public MarcaDto getById(String id_marca)
    {
        try
        {
            MarcaEntity categoria = marcaRepository.findById(UUID.fromString(id_marca)).orElseThrow(() -> new NotFoundException("Marca no encontrada"));

            return mapper.map(categoria, MarcaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException) throw new NotFoundException(e.getMessage());

            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public MarcaDto create(CreateMarcaDto marcaDto)
    {
        //Capitalizando el nombre de la marca (primera letra en mayúscula)
        String capitalizedName = capitalize.CapitalizeText(marcaDto.getNombre());
        marcaDto.setNombre(capitalizedName);

        //Buscando si una marca con el mismo nombre ya existe
        MarcaEntity findedMarca = marcaRepository.getByNombre(capitalizedName);

        //Verificando si se encontró la marca y lanzando el error correspondiente si así es
        if (findedMarca != null)
            throw new BadRequestException("La marca con el nombre '" + capitalizedName + "' ya existe");

        //Mapeando los datos de la marca a registrar
        MarcaEntity marca = mapper.map(marcaDto, MarcaEntity.class);

        //Guardando la marca en la base de datos y retornando sus dotos
        return mapper.map(marcaRepository.save(marca), MarcaDto.class);
    }

    @Override
    public MarcaDto update(UpdateMarcaDto marcaDto, String id_marca)
    {
        //Capitalizando la primera letra del nombre de la marca
        String capitalizedName = capitalize.CapitalizeText(marcaDto.getNombre());
        marcaDto.setNombre(capitalizedName);

        try
        {
            //Obteniendo la marca por su id
            MarcaEntity marca = marcaRepository.findById(UUID.fromString(id_marca)).orElseThrow(() -> new NotFoundException("La marca con el uuid: " + id_marca + " no fue encontrado"));

            //Verificando si la marca fue eliminada
            if (marca.getEliminado())
                throw new BadRequestException("La marca " + marca.getNombre() + " esta eliminada");

            //Comprobando si el nombre de la entidad en la bd es el mimso que el del dto
            if (!marca.getNombre().equals(capitalizedName))
            {
                //Verificando si el nombre esta disponible
                MarcaEntity marcaByName = marcaRepository.getByNombre(capitalizedName);
                if (marcaByName != null)
                    throw new BadRequestException("Ya existe una marca con el nombre " + capitalizedName);

                //Actualizando información de la entidad
                mapper.map(marcaDto, marca);

                //Actualizando los datos en la bd y retornando el dto
                return mapper.map(marcaRepository.save(marca), MarcaDto.class);

            }

            //Retornando el objeto mismo debido a que el nombre es el mismo
            return mapper.map(marca, MarcaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public MarcaDto delete(String id_marca)
    {
        try
        {
            //Obteniendo la marca por su id
            MarcaEntity marca = marcaRepository.findById(UUID.fromString(id_marca)).orElseThrow(() -> new NotFoundException("La marca con el uuid: " + id_marca + " no fue encontrado"));

            //Verificando si la marca fue eliminada
            if (marca.getEliminado())
                throw new BadRequestException("La marca " + marca.getNombre() + " esta eliminada");

            //Eliminando la marca de forma lógica
            marca.setEliminado(true);

            return mapper.map(marcaRepository.save(marca), MarcaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public MarcaDto restore(String id_marca)
    {
        try
        {
            //Obteniendo la marca por su id
            MarcaEntity marca = marcaRepository.findById(UUID.fromString(id_marca)).orElseThrow(() -> new NotFoundException("La marca con el uuid: " + id_marca + " no fue encontrado"));

            //Verificando si la marca fue eliminada
            if (!marca.getEliminado())
                throw new BadRequestException("La marca " + marca.getNombre() + " no esta eliminada");

            //Restaurando la marca de forma lógica
            marca.setEliminado(false);

            return mapper.map(marcaRepository.save(marca), MarcaDto.class);

        } catch (Exception e)
        {
            if (e instanceof NotFoundException)
                throw new NotFoundException(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
