package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.AtualizarRestauranteInput;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public Restaurante execute(final UUID id, final AtualizarRestauranteInput input) {
        Restaurante restaurante = restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        restaurante.alterarRestaurante(input.nome(), input.tipoCozinha(), input.horarioFuncionamento(), input.endereco());
        restauranteGateway.atualizar(restaurante);

        String nomeDono = restauranteGateway.nomeDonoVinculadoRestaurante(restaurante.getId());
        restaurante.setNomeDono(nomeDono);

        return restaurante;
    }

}
