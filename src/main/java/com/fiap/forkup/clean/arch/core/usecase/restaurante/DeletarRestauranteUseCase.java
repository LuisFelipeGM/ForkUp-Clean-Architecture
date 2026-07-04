package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeletarRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    public void execute(UUID id) {
        validarExclusao(id);
        restauranteGateway.deletar(id);
    }

    private void validarExclusao(UUID id) {
        if (!restauranteGateway.existsRestaurante(id))
            throw new RestauranteNaoEncontradoException("Restaurante não encontrado");
    }

}
