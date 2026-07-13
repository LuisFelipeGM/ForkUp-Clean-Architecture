package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.infra.web.dto.TipoUsuarioResponse;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioRequest;

public class TipoUsuarioMapper {

    public TipoUsuario toDomain(TipoUsuarioRequest request) {
        return new TipoUsuario(request.descricao());
    }

    public TipoUsuarioResponse domainToDto(TipoUsuario tipoUsuario) {
        return new TipoUsuarioResponse(tipoUsuario.getId(), tipoUsuario.getDescricao());
    }
}
