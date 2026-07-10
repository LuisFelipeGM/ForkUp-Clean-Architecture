package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioResponse(
        UUID id,
        String descricao
) {

    public static TipoUsuarioResponse fromDomain(TipoUsuario tipoUsuario) {
        return new TipoUsuarioResponse(
                tipoUsuario.getId(),
                tipoUsuario.getDescricao()
        );
    }

}
