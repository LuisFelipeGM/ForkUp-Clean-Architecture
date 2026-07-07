package com.fiap.forkup.clean.arch.core.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioResponse (
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean apenasRestaurante,
        String pathFoto
) { }
