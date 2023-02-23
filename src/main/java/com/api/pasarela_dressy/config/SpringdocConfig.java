package com.api.pasarela_dressy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {
    @Bean
    public OpenAPI baseOpenApi(){
        return new OpenAPI().
                info(new Info().title("Pasarela Dressy Doc").version("1.0.").description("Documentación de la api de Pasarela Dressy"));
    }
}
