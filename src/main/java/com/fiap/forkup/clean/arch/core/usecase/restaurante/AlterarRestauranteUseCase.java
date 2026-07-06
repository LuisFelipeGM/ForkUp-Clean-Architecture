package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.dto.RestauranteRequestUpdate;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    private final RestauranteMapper restauranteMapper;

    private final EnderecoMapper enderecoMapper;

    public RestauranteResponseFull execute(final UUID id, final RestauranteRequestUpdate restauranteRequestUpdate) {
        Restaurante restaurante = restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        Endereco endereco = enderecoMapper.toDomain(restauranteRequestUpdate.endereco());

        restaurante.alterarRestaurante(restauranteRequestUpdate.nome(), restauranteRequestUpdate.tipoCozinha(), restauranteRequestUpdate.horarioFuncionamento(), endereco);
        restauranteGateway.atualizar(restaurante);

        String nomeGerente = restauranteGateway.nomeGerenteVinculadoRestaurante(restaurante.getId());

        return restauranteMapper.domainToDtoFull(restaurante, nomeGerente);
    }

}
