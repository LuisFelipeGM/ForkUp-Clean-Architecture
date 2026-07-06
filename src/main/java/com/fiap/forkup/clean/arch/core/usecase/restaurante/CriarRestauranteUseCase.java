package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.RestauranteRequestCreate;
import com.fiap.forkup.clean.arch.core.exception.UsuarioGerenteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CriarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    private final UsuarioGateway usuarioGateway;

    private final RestauranteMapper restauranteMapper;

    public UUID execute(final RestauranteRequestCreate restauranteRequestCreate) {
        validarCriacao(restauranteRequestCreate);

        Restaurante restaurante = restauranteMapper.requestToDomain(restauranteRequestCreate);

       return restauranteGateway.criar(restaurante);
    }

    private void validarCriacao(final RestauranteRequestCreate restauranteRequestCreate) {
        if (!usuarioGateway.existsUsuarioGerente(restauranteRequestCreate.gerenteId())) {
            throw new UsuarioGerenteNaoEncontradoException("Gerente não encontrado");
        }
    }

}
