package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsuarioEntityMapper {

    private final TipoUsuarioEntityMapper tipoUsuarioEntityMapper;

    private final EnderecoEntityMapper enderecoEntityMapper;

    public UsuarioJpaEntity toEntity(Usuario usuario) {
        if (usuario == null)
            return null;

        return UsuarioJpaEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .login(usuario.getLogin())
                .senha(usuario.getSenha())
                .tipoUsuario(tipoUsuarioEntityMapper.toEntity(usuario.getTipoUsuario()))
                .endereco(enderecoEntityMapper.toEntity(usuario.getEndereco()))
                .build();
    }

    public Usuario toDomain(UsuarioJpaEntity jpaEntity) {
        if (jpaEntity == null)
            return null;

        return new Usuario(
                jpaEntity.getId(),
                jpaEntity.getNome(),
                jpaEntity.getEmail(),
                jpaEntity.getLogin(),
                jpaEntity.getSenha(),
                tipoUsuarioEntityMapper.toDomain(jpaEntity.getTipoUsuario()),
                enderecoEntityMapper.toDomain(jpaEntity.getEndereco())
        );
    }

}
