package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.AtualizarItemCardapioInput;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AtualizarItemCardapioUseCase {

    private final RestauranteGateway restauranteGateway;

    public ItemCardapio execute(UUID restauranteId, UUID itemId, AtualizarItemCardapioInput input) {

        Restaurante restaurante = restauranteGateway.buscarPorIdComCardapio(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        ItemCardapio item = new ItemCardapio(itemId, input.nome(), input.descricao(), input.preco(), input.apenasRestaurante(), input.pathFoto());
        restaurante.atualizarItemCardapio(itemId, item);

        restauranteGateway.atualizar(restaurante);

        return item;
    }

}
