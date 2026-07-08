package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoEntityMapper {

    public EnderecoJpaEntity toEntity(Endereco endereco) {
        if (endereco == null)
            return null;

        return EnderecoJpaEntity.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .build();
    }

    public Endereco toDomain(EnderecoJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        return new Endereco(
                jpaEntity.getId(),
                jpaEntity.getLogradouro(),
                jpaEntity.getNumero(),
                jpaEntity.getComplemento(),
                jpaEntity.getCidade(),
                jpaEntity.getCep()
        );
    }

}
