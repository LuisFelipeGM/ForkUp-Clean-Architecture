package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AtualizarItemCardapioUseCase {

    private final RestauranteGateway restauranteGateway;

    public ItemCardapio execute(UUID restauranteId, UUID itemId, ItemCardapio itemCardapio) {

        Restaurante restaurante = restauranteGateway.buscarPorIdComCardapio(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        restaurante.atualizarItemCardapio(itemId, itemCardapio);

        restauranteGateway.atualizar(restaurante);

        return itemCardapio;
    }

}
