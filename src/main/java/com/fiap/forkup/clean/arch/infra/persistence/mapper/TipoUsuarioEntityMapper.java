package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.TipoUsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioEntityMapper {

    public TipoUsuarioJpaEntity toEntity(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null)
            return null;

        return TipoUsuarioJpaEntity.builder()
                .id(tipoUsuario.getId())
                .descricao(tipoUsuario.getDescricao())
                .dataCriacao(tipoUsuario.getCriadoEm())
                .build();
    }

    public TipoUsuario toDomain(TipoUsuarioJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        return new TipoUsuario(
                jpaEntity.getId(),
                jpaEntity.getDescricao(),
                jpaEntity.getDataCriacao()
        );
    }

}
