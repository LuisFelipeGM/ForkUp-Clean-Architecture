package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.Endereco;

public record AtualizarUsuarioInput(
    String nome,
    String email,
    String login,
    Endereco endereco
) { }
