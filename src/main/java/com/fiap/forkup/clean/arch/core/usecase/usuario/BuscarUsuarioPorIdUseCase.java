package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(UUID id) {
        return usuarioGateway
                .buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

}
