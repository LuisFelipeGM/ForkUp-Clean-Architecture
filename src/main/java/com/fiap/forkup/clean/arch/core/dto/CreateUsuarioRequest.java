package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record CreateUsuarioRequest(
        String nome,
        String email,
        String login,
        String senha,
        UUID tipoUsuario,
        EnderecoRequest endereco
){ }
