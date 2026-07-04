package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record RestauranteResponsePartial(
        UUID id,
        String nomeRestaurante,
        String tipoCozinha,
        String horarioFuncionamento,
) { }