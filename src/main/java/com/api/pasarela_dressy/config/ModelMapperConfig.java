package com.api.pasarela_dressy.config;

import com.api.pasarela_dressy.utils.mappers.DetalleEntradaMapper;
import com.api.pasarela_dressy.utils.mappers.EntradaMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ModelMapperConfig {

    @Autowired
    DetalleEntradaMapper detalleEntradaMapper;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        //Configurando model maper para mapear de una instancia a otra
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
