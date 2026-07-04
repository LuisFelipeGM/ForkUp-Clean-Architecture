package com.fiap.forkup.clean.arch.core.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.TipoUsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ListarTiposUsuarioUseCase {

    private final TipoUsuarioGateway tipoUsuarioGateway;

    private final TipoUsuarioMapper tipoUsuarioMapper;

    public List<TipoUsuarioReponse> executar() {
        return tipoUsuarioGateway.listarTodos()
                .stream()
                .map(tipoUsuarioMapper::domainToDto)
                .collect(Collectors.toList());
    }

}
