package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.exception.UsuarioComRestauranteVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class DeletarUsuarioUseCase {

    private UsuarioGateway usuarioGateway;

    public void executar(UUID id) {
        validarExclusao(id);
        usuarioGateway.deletar(id);
    }

    private void validarExclusao(UUID id) {
        if (!usuarioGateway.existsUsuario(id)) {
            log.error("Usuário não encontrado com o ID: {}", id);
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        if (usuarioGateway.existsRestauranteVinculadoUsuario(id)) {
            log.error("Não é possível excluir o usuário com ID: {} pois existem restaurantes vinculados a ele.", id);
            throw new UsuarioComRestauranteVinculadoException("Não é possível excluir o usuário pois existem restaurantes vinculados a ele.");
        }
    }

}
