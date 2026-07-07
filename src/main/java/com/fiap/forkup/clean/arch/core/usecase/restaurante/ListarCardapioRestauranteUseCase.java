package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ListarCardapioRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public List<ItemCardapio> execute(UUID idRestaurante) {
        validar(idRestaurante);
        return restauranteGateway.listarCardapio(idRestaurante);
    }

    private void validar(UUID id) {
        if (!restauranteGateway.existsRestaurante(id))
            throw new RestauranteNaoEncontradoException("Restaurante não encontrado");
    }

}
