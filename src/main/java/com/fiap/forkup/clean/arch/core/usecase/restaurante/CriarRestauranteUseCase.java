package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.UsuarioDonoNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CriarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    private final UsuarioGateway usuarioGateway;

    public UUID execute(final Restaurante restaurante) {
        validarCriacao(restaurante);
        return restauranteGateway.criar(restaurante);
    }

    private void validarCriacao(final Restaurante restaurante) {
        if (!usuarioGateway.existsUsuarioDono(restaurante.getDono())) {
            throw new UsuarioDonoNaoEncontradoException("Dono do Restaurante não encontrado");
        }
    }

}
