package com.fiap.forkup.clean.arch.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record EnderecoResponse(
        @Schema(description = "ID do endereço", example = "f718f2cb-b98d-4c2e-8d03-8b293cd91490")
        UUID id,

        @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
        String logradouro,

        @Schema(description = "Número do endereço", example = "123")
        String numero,

        @Schema(description = "Complemento do endereço", example = "Apto 101")
        String complemento,

        @Schema(description = "Cidade do endereço", example = "São Paulo")
        String cidade,

        @Schema(description = "CEP do endereço", example = "12345-678")
        String cep
) { }