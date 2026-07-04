package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioComUsuarioVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeletarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public void executar(UUID id) {
        validarExclusao(id);
        tipoUsuarioGateway.deletar(id);
    }

    private void validarExclusao(UUID id) {
        if (!tipoUsuarioGateway.existsTipoUsuario(id))
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuário não encontrado");

        if (tipoUsuarioGateway.existsUsuarioComEsteTipo(id))
            throw new TipoUsuarioComUsuarioVinculadoException("Não é possível excluir o tipo de usuário pois existem usuários associados a ele.");
    }

}
