package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record UsuarioResponseFull(
        UUID id,
        String nome,
        String email,
        String login,
        TipoUsuarioResponse tipoUsuario,
        EnderecoResponse endereco
) { }
