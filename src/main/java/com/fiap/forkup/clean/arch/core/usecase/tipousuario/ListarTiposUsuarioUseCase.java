package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class ListarTiposUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public List<TipoUsuario> execute() {
        return tipoUsuarioGateway.listarTodos();
    }

}
