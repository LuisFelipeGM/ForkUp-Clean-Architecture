package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class DeletarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public void executar(UUID id) {
        validarExclusao(id);
        tipoUsuarioGateway.deletar(id);
    }

    private void validarExclusao(UUID id) {
        if (!tipoUsuarioGateway.existsTipoUsuario(id)) {
            log.error("Tipo de usuário não encontrado com o ID: {}", id);
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuário não encontrado");
        }

        if (tipoUsuarioGateway.existsUsuarioComEsteTipo(id)) {
            log.error("Não é possível excluir o tipo de usuário com ID: {} pois existem usuários associados a ele.", id);
            throw new IllegalStateException("Não é possível excluir o tipo de usuário pois existem usuários associados a ele.");
        }
    }

}
