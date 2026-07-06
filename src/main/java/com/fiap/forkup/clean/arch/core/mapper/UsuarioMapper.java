package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.UsuarioRequestCreate;
import com.fiap.forkup.clean.arch.core.dto.UsuarioReponseFull;
import com.fiap.forkup.clean.arch.core.dto.UsuarioResponsePartial;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UsuarioMapper {

    private EnderecoMapper enderecoMapper;

    private TipoUsuarioMapper tipoUsuarioMapper;

    public Usuario requestToDomain(UsuarioRequestCreate request, TipoUsuario tipoUsuario) {
        return new Usuario(
                UUID.randomUUID(),
                request.nome(),
                request.email(),
                request.login(),
                request.senha(),
                tipoUsuario,
                enderecoMapper.toDomain(request.endereco())
        );
    }

    public UsuarioReponseFull domainToDtoFull(Usuario usuario) {
        return new UsuarioReponseFull(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                tipoUsuarioMapper.domainToDto(usuario.getTipoUsuario()),
                enderecoMapper.domainToDto(usuario.getEndereco())
        );
    }

    public UsuarioResponsePartial domainToDtoPartial(Usuario usuario) {
        return new UsuarioResponsePartial(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario().getDescricao()
        );
    }

}
