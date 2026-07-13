package com.fiap.forkup.clean.arch.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UsuarioResponseFull(
        @Schema(description = "Identificador único do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome completo do usuário", example = "João da Silva")
        String nome,

        @Schema(description = "Endereço de email do usuário", example = "joao.silva@example.com")
        String email,

        @Schema(description = "Nome de login do usuário", example = "joao.silva")
        String login,

        @Schema(description = "Tipo de usuário")
        TipoUsuarioResponse tipoUsuario,

        @Schema(description = "Endereço do usuário")
        EnderecoResponse endereco
) { }
