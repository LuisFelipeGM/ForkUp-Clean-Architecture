package com.fiap.forkup.clean.arch.core.dto;

import java.math.BigDecimal;

public record AtualizarItemCardapioInput(
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean apenasRestaurante,
        String pathFoto
) { }
