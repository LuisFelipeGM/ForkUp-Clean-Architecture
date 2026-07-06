package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class BuscarRestaurantePorIdUseCase {

    private final RestauranteGateway restauranteGateway;

    private final RestauranteMapper restauranteMapper;

    public RestauranteResponseFull execute(UUID id) {
       Restaurante restaurante = restauranteGateway.buscarPorId(id)
               .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

       String nomeGerente = restauranteGateway.nomeGerenteVinculadoRestaurante(restaurante.getId());

       return restauranteMapper.domainToDtoFull(restaurante, nomeGerente);
    }

}
