package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.UsuarioComRestauranteVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarTipoDoUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public Usuario execute(UUID idUsuario, UUID idTipoUsuario) {
        Usuario usuario = usuarioGateway.buscarPorId(idUsuario).orElseThrow(
                () -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(idTipoUsuario).orElseThrow(
                () -> new UsuarioNaoEncontradoException("Tipo de usuário não encontrado"));

        validarAlterarTipoUsuario(idUsuario);

        usuario.atualizarTipoUsuario(tipoUsuario);
        usuarioGateway.atualizar(usuario);

        return usuario;
    }

    private void validarAlterarTipoUsuario(UUID idUsuario) {
        if (usuarioGateway.usuarioVinculadoRestaurante(idUsuario)) {
            throw new UsuarioComRestauranteVinculadoException("Não é possível alterar o tipo do usuário pois existem restaurantes vinculados a ele.");
        }
    }


}
