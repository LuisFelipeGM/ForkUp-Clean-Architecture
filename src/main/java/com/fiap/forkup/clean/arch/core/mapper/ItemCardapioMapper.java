package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.dto.ItemCardapioRequest;
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

}
