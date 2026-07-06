package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CriarTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public UUID executar(TipoUsuario tipoUsuario) {
        validarTipoUsuarioUnico(tipoUsuario);
        return tipoUsuarioGateway.criar(tipoUsuario);
    }

    private void validarTipoUsuarioUnico(TipoUsuario tipoUsuario) {
        if(tipoUsuarioGateway.existsByDescricao(tipoUsuario.getDescricao()))
            throw new TipoUsuarioJaCadastradoException("Tipo Usuário já cadastrado: " + tipoUsuario.getDescricao());
    }

}
