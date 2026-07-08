package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.ItemCardapioJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemCardapioEntityMapper {

    public ItemCardapioJpaEntity toEntity(ItemCardapio itemCardapio) {
        if (itemCardapio == null)
            return null;

        return ItemCardapioJpaEntity.builder()
                .id(itemCardapio.getId())
                .nome(itemCardapio.getNome())
                .descricao(itemCardapio.getDescricao())
                .preco(itemCardapio.getPreco())
                .apenasRestaurante(itemCardapio.getApenasRestaurante())
                .fotoUrl(itemCardapio.getPathFoto())
                .build();
    }

    public ItemCardapio toDomain(ItemCardapioJpaEntity itemCardapioJpaEntity) {
        if (itemCardapioJpaEntity == null)
            return null;

        return new ItemCardapio(
                itemCardapioJpaEntity.getId(),
                itemCardapioJpaEntity.getNome(),
                itemCardapioJpaEntity.getDescricao(),
                itemCardapioJpaEntity.getPreco(),
                itemCardapioJpaEntity.getApenasRestaurante(),
                itemCardapioJpaEntity.getFotoUrl()
        );
    }

    public List<ItemCardapioJpaEntity> toEntityList(List<ItemCardapio> cardapio) {
        if (cardapio == null) {
            return List.of();
        }

        return cardapio.stream()
                .map(this::toEntity)
                .toList();
    }

    public List<ItemCardapio> toDomainList(List<ItemCardapioJpaEntity> cardapio) {
        if (cardapio == null) {
            return List.of();
        }

        return cardapio.stream()
                .map(this::toDomain)
                .toList();
    }

}