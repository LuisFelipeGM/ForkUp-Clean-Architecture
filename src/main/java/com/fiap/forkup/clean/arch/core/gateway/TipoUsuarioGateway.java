package com.fiap.forkup.clean.arch.core.gateway;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;

import java.util.UUID;

public interface TipoUsuarioGateway {

    UUID criar(TipoUsuario tipoUsuario);

    boolean existsByDescricao(String descricao);

}
