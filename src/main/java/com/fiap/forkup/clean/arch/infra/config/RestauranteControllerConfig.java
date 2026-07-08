package com.fiap.forkup.clean.arch.infra.config;

import com.fiap.forkup.clean.arch.core.controller.RestauranteController;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.ItemCardapioMapper;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestauranteControllerConfig {

    @Bean
    public RestauranteController restauranteController(
            ListarRestauranteUseCase listarRestauranteUseCase,
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            CriarRestauranteUseCase criarRestauranteUseCase,
            AlterarRestauranteUseCase alterarRestauranteUseCase,
            AlterarDonoRestauranteUseCase alterarDonoRestauranteUseCase,
            DeletarRestauranteUseCase deletarRestauranteUseCase,
            ListarCardapioRestauranteUseCase listarCardapioRestauranteUseCase,
            AdicionarItemCardapioUseCase adicionarItemCardapioUseCase,
            AtualizarItemCardapioUseCase atualizarItemCardapioUseCase,
            RemoverItemCardapioUseCase removerItemCardapioUseCase,
            RestauranteMapper restauranteMapper,
            ItemCardapioMapper itemCardapioMapper,
            EnderecoMapper enderecoMapper) {
        return new RestauranteController(
                listarRestauranteUseCase,
                buscarRestaurantePorIdUseCase,
                criarRestauranteUseCase,
                alterarRestauranteUseCase,
                alterarDonoRestauranteUseCase,
                deletarRestauranteUseCase,
                listarCardapioRestauranteUseCase,
                adicionarItemCardapioUseCase,
                atualizarItemCardapioUseCase,
                removerItemCardapioUseCase,
                restauranteMapper,
                itemCardapioMapper,
                enderecoMapper);
    }

}
