package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioResponse(
        UUID id,
        String descricao
) { }
