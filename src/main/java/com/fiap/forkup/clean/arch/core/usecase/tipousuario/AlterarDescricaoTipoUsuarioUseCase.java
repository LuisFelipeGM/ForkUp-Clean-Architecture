package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarDescricaoTipoUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public TipoUsuario executar(UUID id, String novaDescricao) {
        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException("Tipo Usuário não encontrado"));

        validarTipoUsuarioUnico(novaDescricao);

        tipoUsuario.alterarDescricao(novaDescricao);
        tipoUsuarioGateway.atualizar(tipoUsuario);

        return tipoUsuario;
    }

    private void validarTipoUsuarioUnico(String novaDescricao) {
        if(tipoUsuarioGateway.existsByDescricao(novaDescricao))
            throw new TipoUsuarioJaCadastradoException("Tipo Usuário já cadastrado: " + novaDescricao);
    }

}
