package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record UsuarioResponsePartial(
        UUID id,
        String nome,
        String email,
        String login,
        String tipoUsuario
) { }
