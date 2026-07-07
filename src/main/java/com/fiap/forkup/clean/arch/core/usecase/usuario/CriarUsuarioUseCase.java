package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public UUID execute(Usuario usuario) {
        validarCriacao(usuario);

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(usuario.getTipoUsuario().getId())
                .orElseThrow(() -> {
                    log.error("Tipo Usuário não encontrado com o ID: {}", usuario.getTipoUsuario().getId());
                    return new TipoUsuarioNaoEncontradoException("Tipo Usuário não encontrado");
                });

        usuario.atualizarTipoUsuario(tipoUsuario);

        return usuarioGateway.criar(usuario);
    }

    private void validarCriacao(final Usuario usuario) {
        if (usuarioGateway.existsUsuarioComEsteLogin(usuario.getLogin())) {
            log.error("Login já cadastrado: {}", usuario.getLogin());
            throw new LoginUsuarioJaCadastradoException("Login já cadastrado");
        }

        if (usuarioGateway.existsUsuarioComEsteEmail(usuario.getEmail())) {
            log.error("Email já cadastrado: {}", usuario.getEmail());
            throw new EmailUsuarioJaCadastradoException("Email já cadastrado");
        }

    }

}
