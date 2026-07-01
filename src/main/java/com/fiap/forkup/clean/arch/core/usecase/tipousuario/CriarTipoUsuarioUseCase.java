package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CriarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public UUID executar(TipoUsuario tipoUsuario) {
        validarTipoUsuarioUnico(tipoUsuario);
        return tipoUsuarioGateway.criar(tipoUsuario);
    }

    public void validarTipoUsuarioUnico(TipoUsuario tipoUsuario) {
        if(tipoUsuarioGateway.existsByDescricao(tipoUsuario.getDescricao())) {
            log.error("Tipo Usuário já cadastrado com a descrição: {}", tipoUsuario.getDescricao());
            throw new TipoUsuarioJaCadastradoException("Tipo Usuário já cadastrado: " + tipoUsuario.getDescricao());
        }
    }

}
