package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.DonoJaVinculadoRestauranteException;
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

    public Restaurante execute(UUID idRestaurante, UUID idDono) {
        var restaurante = restauranteGateway.buscarPorId(idRestaurante).orElseThrow(() -> {
            throw new RestauranteNaoEncontradoException("Restaurante não encontrado");
        });

        validarDono(idDono);

        restaurante.alterarDono(idDono);
        restauranteGateway.atualizar(restaurante);

        String nomeDono = restauranteGateway.nomeDonoVinculadoRestaurante(restaurante.getId());
        restaurante.setNomeDono(nomeDono);

        return restaurante;
    }

    private void validarDono(UUID idDono) {
        if (!usuarioGateway.existsUsuarioDono(idDono)) {
            throw new UsuarioDonoNaoEncontradoException("Dono do Restaurante não encontrado");
        }

        if (usuarioGateway.existsRestauranteVinculadoUsuario(idDono)) {
            throw new DonoJaVinculadoRestauranteException("Não é possível criar o restaurante pois o dono já está vinculado a outro restaurante.");
        }
    }

}
