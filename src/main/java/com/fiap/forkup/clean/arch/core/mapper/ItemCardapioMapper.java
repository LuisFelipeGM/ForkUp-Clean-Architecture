package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.dto.ItemCardapioRequest;
import com.fiap.forkup.clean.arch.core.dto.ItemCardapioResponse;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ItemCardapioMapper {

    public ItemCardapio requestToDomain(ItemCardapioRequest request) {
        return new ItemCardapio(
                UUID.randomUUID(),
                request.nome(),
                request.descricao(),
                request.preco(),
                request.apenasRestaurante(),
                request.pathFoto()
        );
    }

    public ItemCardapioResponse domainToDto(ItemCardapio item) {
        return new ItemCardapioResponse(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getPreco(),
                item.getApenasRestaurante(),
                item.getPathFoto()
        );
    }

}
