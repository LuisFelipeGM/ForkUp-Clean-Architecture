package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioDonoNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarDonoRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    private final UsuarioGateway usuarioGateway;

    public Restaurante execute(UUID idRestaurante, UUID idGerente) {
        var restaurante = restauranteGateway.buscarPorId(idRestaurante).orElseThrow(() -> {
            throw new RestauranteNaoEncontradoException("Restaurante não encontrado");
        });

        validarDono(idGerente);

        restaurante.alterarGerente(idGerente);
        restauranteGateway.atualizar(restaurante);

        String nomeGerente = restauranteGateway.nomeDonoVinculadoRestaurante(restaurante.getId());
        restaurante.setNomeDono(nomeGerente);

        return restaurante;
    }

    private void validarDono(UUID idGerente) {
        if (!usuarioGateway.existsUsuarioDono(idGerente)) {
            throw new UsuarioDonoNaoEncontradoException("Dono do Restaurante não encontrado");
        }
    }

}
