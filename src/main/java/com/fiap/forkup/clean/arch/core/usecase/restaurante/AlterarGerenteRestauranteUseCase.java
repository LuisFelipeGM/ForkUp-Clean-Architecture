package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioGerenteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarGerenteRestauranteUseCase {

    private RestauranteGateway restauranteGateway;

    private UsuarioGateway usuarioGateway;

    private RestauranteMapper restauranteMapper;

    public RestauranteResponseFull execute(UUID idRestaurante, UUID idGerente) {
        var restaurante = restauranteGateway.buscarPorId(idRestaurante).orElseThrow(() -> {
            throw new RestauranteNaoEncontradoException("Restaurante não encontrado");
        });

        validarGerente(idGerente);

        restaurante.alterarGerente(idGerente);
        restauranteGateway.atualizar(restaurante);

        String nomeGerente = restauranteGateway.nomeGerenteVinculadoRestaurante(restaurante.getId());

        return restauranteMapper.domainToDtoFull(restaurante, nomeGerente);
    }

    private void validarGerente(UUID idGerente) {
        if (!usuarioGateway.existsUsuarioGerente(idGerente)) {
            throw new UsuarioGerenteNaoEncontradoException("Gerente não encontrado");
        }
    }

}
