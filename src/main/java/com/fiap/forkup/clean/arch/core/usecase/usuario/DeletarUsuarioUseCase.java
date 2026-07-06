package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.exception.UsuarioComRestauranteVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeletarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public void execute(UUID id) {
        validarExclusao(id);
        usuarioGateway.deletar(id);
    }

    private void validarExclusao(UUID id) {
        if (!usuarioGateway.existsUsuario(id))
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");


        if (usuarioGateway.existsRestauranteVinculadoUsuario(id))
            throw new UsuarioComRestauranteVinculadoException("Não é possível excluir o usuário pois existem restaurantes vinculados a ele.");

    }

}
