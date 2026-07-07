package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RemoverItemCardapioUseCase {

    private final RestauranteGateway restauranteGateway;

    public void execute(UUID restauranteId, UUID itemCardapioId) {
        Restaurante restaurante = restauranteGateway.buscarPorIdComCardapio(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        restaurante.removerItemCardapio(itemCardapioId);

        restauranteGateway.atualizar(restaurante);
    }

}
