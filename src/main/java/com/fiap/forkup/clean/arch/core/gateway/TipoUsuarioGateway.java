package com.fiap.forkup.clean.arch.core.gateway;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioGateway {

    Optional<TipoUsuario> buscarPorId(UUID id);

    List<TipoUsuario> listarTodos();

    UUID criar(TipoUsuario tipoUsuario);

    void atualizar(TipoUsuario tipoUsuario);

    boolean existsTipoUsuario(UUID id);

    boolean existsByDescricao(String descricao);

    boolean existsUsuarioComEsteTipo(UUID id);

    void deletar(UUID id);

}
