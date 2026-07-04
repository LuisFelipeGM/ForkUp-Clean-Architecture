package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.UsuarioRequestCreate;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class CriarUsuarioUseCase {

    private UsuarioGateway usuarioGateway;

    private TipoUsuarioGateway tipoUsuarioGateway;

    private UsuarioMapper usuarioMapper;

    public UUID execute(final UsuarioRequestCreate usuarioRequestCreate) {
        validarCriacao(usuarioRequestCreate);

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(usuarioRequestCreate.tipoUsuario())
                .orElseThrow(() -> {
                    log.error("Tipo Usuário não encontrado com o ID: {}", usuarioRequestCreate.tipoUsuario());
                    return new TipoUsuarioNaoEncontradoException("Tipo Usuário não encontrado");
                });

        Usuario usuario = usuarioMapper.requestToDomain(usuarioRequestCreate, tipoUsuario);

        return usuarioGateway.criar(usuario);
    }

    private void validarCriacao(final UsuarioRequestCreate usuarioRequestCreate) {
        if (usuarioGateway.existsUsuarioComEsteLogin(usuarioRequestCreate.login())) {
            log.error("Login já cadastrado: {}", usuarioRequestCreate.login());
            throw new LoginUsuarioJaCadastradoException("Login já cadastrado");
        }

        if (usuarioGateway.exitsUsuarioComEsteEmail(usuarioRequestCreate.email())) {
            log.error("Email já cadastrado: {}", usuarioRequestCreate.email());
            throw new EmailUsuarioJaCadastradoException("Email já cadastrado");
        }

    }

}
