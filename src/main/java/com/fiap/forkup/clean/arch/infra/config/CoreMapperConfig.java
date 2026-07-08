package com.fiap.forkup.clean.arch.infra.config;

import com.fiap.forkup.clean.arch.core.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreMapperConfig {

    @Bean
    public EnderecoMapper enderecoMapper() {
        return new EnderecoMapper();
    }

    @Bean
    public TipoUsuarioMapper tipoUsuarioMapper() {
        return new TipoUsuarioMapper();
    }

    @Bean
    public UsuarioMapper usuarioMapper(EnderecoMapper enderecoMapper, TipoUsuarioMapper tipoUsuarioMapper) {
        return new UsuarioMapper(enderecoMapper, tipoUsuarioMapper);
    }

    @Bean
    public ItemCardapioMapper itemCardapioMapper() {
        return new ItemCardapioMapper();
    }

    @Bean
    public RestauranteMapper restauranteMapper(EnderecoMapper enderecoMapper) {
        return new RestauranteMapper(enderecoMapper);
    }

}
