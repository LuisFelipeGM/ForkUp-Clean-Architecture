package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.ItemCardapioRequest;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.mapper.ItemCardapioMapper;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AtualizarItemCardapioUseCase {

    private final RestauranteGateway restauranteGateway;

    private final ItemCardapioMapper itemCardapioMapper;

    public void execute(UUID restauranteId, UUID itemId, ItemCardapioRequest itemCardapioRequest) {

        Restaurante restaurante = restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        ItemCardapio itemCardapio = itemCardapioMapper.requestToDomain(itemCardapioRequest);

        restaurante.atualizarItemCardapio(itemId, itemCardapio);

        restauranteGateway.atualizar(restaurante);

    }

}
