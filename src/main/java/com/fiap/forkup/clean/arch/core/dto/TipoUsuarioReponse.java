package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioReponse(
        UUID id,
        String descricao
) {

    public static TipoUsuarioReponse fromDomain(TipoUsuario tipoUsuario) {
        return new TipoUsuarioReponse(
                tipoUsuario.getId(),
                tipoUsuario.getDescricao()
        );
    }

}
