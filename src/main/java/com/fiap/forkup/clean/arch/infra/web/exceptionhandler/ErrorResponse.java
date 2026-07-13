package com.fiap.forkup.clean.arch.infra.web.exceptionhandler;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorResponse(
        @Schema(description = "Código de status HTTP da resposta", example = "400")
        int status,

        @Schema(description = "Tipo de erro", example = "Bad Request")
        String error,

        @Schema(description = "Mensagem de erro", example = "Dados inválidos")
        String message,

        @Schema(description = "Timestamp do erro", example = "2026-01-01T12:00:00")
        LocalDateTime timestamp
) { }
