package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class BuscarTipoUsuarioPorIdUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public TipoUsuario executar(UUID id) {
        return tipoUsuarioGateway.buscarPorId(id).orElseThrow(() -> {
            log.error("Tipo Usuário não encontrado com o ID: {}", id);
            return new TipoUsuarioNaoEncontradoException("Tipo Usuário não encontrado");
        });
    }

}
