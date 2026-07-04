package com.fiap.forkup.clean.arch.core.dto;

public record EnderecoRequest(
        String logradouro,
        String numero,
        String complemento,
        String cidade,
        String cep
) { }
