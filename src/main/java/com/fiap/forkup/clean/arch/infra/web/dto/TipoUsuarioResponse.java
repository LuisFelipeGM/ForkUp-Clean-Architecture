package com.fiap.forkup.clean.arch.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record TipoUsuarioResponse(
        @Schema(description = "ID do tipo de usuário", example = "b84d7424-bfa4-43bb-b423-98955da03499")
        UUID id,

        @Schema(description = "Descrição do tipo de usuário", example = "Cliente")
        String descricao
) { }
