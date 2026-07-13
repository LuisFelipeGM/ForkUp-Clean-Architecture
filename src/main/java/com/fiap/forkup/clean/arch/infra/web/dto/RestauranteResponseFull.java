package com.fiap.forkup.clean.arch.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record RestauranteResponseFull(
        @Schema(description = "Identificador único do restaurante", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome do restaurante", example = "Restaurante Saboroso")
        String nomeRestaurante,

        @Schema(description = "Tipo de cozinha", example = "Brasileira")
        String tipoCozinha,

        @Schema(description = "Horário de funcionamento", example = "18:00 - 22:00")
        String horarioFuncionamento,

        @Schema(description = "Nome do dono", example = "João da Silva")
        String nomeDono,

        @Schema(description = "Endereço do restaurante")
        EnderecoResponse endereco
) {
}
