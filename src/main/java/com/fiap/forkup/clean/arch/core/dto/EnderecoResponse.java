package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.Endereco;

import java.util.UUID;

public record EnderecoResponse(
        UUID id,
        String logradouro,
        String numero,
        String complemento,
        String cidade,
        String cep
) { }