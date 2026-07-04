package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.dto.UsuarioReponseFull;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class BuscarUsuarioPorIdUseCase {

    private UsuarioGateway usuarioGateway;

    private UsuarioMapper usuarioMapper;

    public UsuarioReponseFull execute(UUID id) {
        return usuarioGateway
                .buscarPorId(id)
                .map(usuarioMapper::domainToDtoFull)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

}
