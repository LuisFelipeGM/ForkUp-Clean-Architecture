package com.fiap.forkup.clean.arch.core.dto;

public record UpdateUsuarioRequest(
        String nome,
        String email,
        String login,
        EnderecoRequest endereco
) {
}
