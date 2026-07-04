package com.fiap.forkup.clean.arch.core.dto;

import java.math.BigDecimal;

public record ItemCardapioRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean apenasRestaurante,
        String pathFoto
) { }