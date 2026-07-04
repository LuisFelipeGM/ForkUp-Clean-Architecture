package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.ItemCardapioRequest;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.mapper.ItemCardapioMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AdicionarItemCardapioUseCase {

    private final RestauranteGateway restauranteGateway;

    private final ItemCardapioMapper itemCardapioMapper;

    public UUID execute(UUID restauranteId, ItemCardapioRequest itemCardapioRequest) {

        Restaurante restaurante = restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Restaurante não encontrado"));

        ItemCardapio item = itemCardapioMapper.requestToDomain(itemCardapioRequest);

        restaurante.adicionarItemCardapio(item);

        restauranteGateway.atualizar(restaurante);

        return item.getId();
    }



}
