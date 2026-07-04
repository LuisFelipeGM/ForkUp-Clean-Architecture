package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record UsuarioRequestCreate(
        String nome,
        String email,
        String login,
        String senha,
        UUID tipoUsuario,
        EnderecoRequest endereco
){ }