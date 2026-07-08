package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RestauranteEntityMapper {

    private final EnderecoEntityMapper enderecoEntityMapper;

    private final ItemCardapioEntityMapper itemCardapioEntityMapper;

    public RestauranteJpaEntity toEntity(Restaurante restaurante) {
        if (restaurante == null)
            return null;

        return RestauranteJpaEntity.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .enderecoJpaEntity(enderecoEntityMapper.toEntity(restaurante.getEndereco()))
                .dono(UsuarioJpaEntity.builder().id(restaurante.getDono()).build())
                .cardapio(itemCardapioEntityMapper.toEntityList(restaurante.getCardapio()))
                .build();
    }

    public Restaurante toDomain(RestauranteJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        return new Restaurante(
                jpaEntity.getId(),
                jpaEntity.getNome(),
                jpaEntity.getTipoCozinha(),
                jpaEntity.getHorarioFuncionamento(),
                enderecoEntityMapper.toDomain(jpaEntity.getEnderecoJpaEntity()),
                jpaEntity.getDono().getId(),
                itemCardapioEntityMapper.toDomainList(jpaEntity.getCardapio())
        );
    }

}
