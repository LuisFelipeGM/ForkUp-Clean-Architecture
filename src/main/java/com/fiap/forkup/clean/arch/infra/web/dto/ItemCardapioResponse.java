package com.fiap.forkup.clean.arch.infra.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioResponse (
        @Schema(description = "Identificador único do item do cardápio", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome do item do cardápio", example = "Prato Principal")
        String nome,

        @Schema(description = "Descrição do item do cardápio", example = "Delicioso prato principal")
        String descricao,

        @Schema(description = "Preço do item do cardápio", example = "29.90")
        BigDecimal preco,

        @Schema(description = "Indica se o item é apenas para o restaurante", example = "true")
        Boolean apenasRestaurante,

        @Schema(description = "Caminho da foto do item do cardápio", example = "/fotos/item1.jpg")
        String pathFoto
) { }
