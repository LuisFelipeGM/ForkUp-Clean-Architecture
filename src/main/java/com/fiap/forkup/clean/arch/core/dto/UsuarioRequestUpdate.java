package com.fiap.forkup.clean.arch.core.dto;

public record UsuarioRequestUpdate(
        String nome,
        String email,
        String login,
        EnderecoRequest endereco
) {
}
