package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioRequest;

public class TipoUsuarioMapper {

    public TipoUsuario toDomain(TipoUsuarioRequest request) {
        return new TipoUsuario(request.descricao());
    }

    public TipoUsuarioReponse domainToDto(TipoUsuario tipoUsuario) {
        return new TipoUsuarioReponse(tipoUsuario.getId(), tipoUsuario.getDescricao());
    }
}
