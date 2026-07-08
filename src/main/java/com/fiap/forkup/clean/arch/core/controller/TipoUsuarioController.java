package com.fiap.forkup.clean.arch.core.controller;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioRequest;
import com.fiap.forkup.clean.arch.core.mapper.TipoUsuarioMapper;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TipoUsuarioController {

    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    private final AlterarDescricaoTipoUsuarioUseCase alterarDescricaoTipoUsuarioUseCase;

    private final DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    private final TipoUsuarioMapper tipoUsuarioMapper;

    public List<TipoUsuarioReponse> listarTodos() {
        List<TipoUsuario> tiposUsuario = listarTiposUsuarioUseCase.execute();
        return tiposUsuario.stream()
                .map(tipoUsuarioMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public TipoUsuarioReponse buscarPorId(UUID id) {
        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase.execute(id);
        return tipoUsuarioMapper.domainToDto(tipoUsuario);
    }

    public UUID criar(TipoUsuarioRequest tipoUsuarioRequest) {
        TipoUsuario tipoUsuario = tipoUsuarioMapper.toDomain(tipoUsuarioRequest);
        return criarTipoUsuarioUseCase.executar(tipoUsuario);
    }

    public TipoUsuarioReponse alterarDescricao(UUID id, TipoUsuarioRequest tipoUsuarioRequest) {
        TipoUsuario tipoUsuario = alterarDescricaoTipoUsuarioUseCase.executar(id, tipoUsuarioRequest.descricao());
        return tipoUsuarioMapper.domainToDto(tipoUsuario);
    }

    public void deletar(UUID id) {
        deletarTipoUsuarioUseCase.execute(id);
    }

}
