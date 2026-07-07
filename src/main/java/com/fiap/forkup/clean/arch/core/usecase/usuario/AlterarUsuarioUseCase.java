package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.AtualizarUsuarioInput;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(final UUID id, final AtualizarUsuarioInput input) {
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        validarUpdate(id, input);

        usuario.atualizarUsuario(input.nome(), input.email(), input.login(), input.endereco());
        usuarioGateway.atualizar(usuario);

        return usuario;
    }

    private void validarUpdate(final UUID id, final AtualizarUsuarioInput input) {
        if (usuarioGateway.existsUsuarioComEsteLoginAndIdNot(input.login(), id)) {
            throw new LoginUsuarioJaCadastradoException("Já existe um usuário com este login");
        }

        if (usuarioGateway.existsUsuarioComEsteEmailAndIdNot(input.email(), id)) {
            throw new EmailUsuarioJaCadastradoException("Já existe um usuário com este email");
        }
    }

}
