package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.TipoUsuarioMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class BuscarTipoUsuarioPorIdUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    private final TipoUsuarioMapper tipoUsuarioMapper;

    public TipoUsuarioReponse execute(UUID id) {
        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(id).orElseThrow(() -> new TipoUsuarioNaoEncontradoException("Tipo Usuário não encontrado"));
        return tipoUsuarioMapper.domainToDto(tipoUsuario);
    }

}
