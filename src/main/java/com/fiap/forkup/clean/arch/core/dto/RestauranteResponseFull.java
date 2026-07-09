package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record RestauranteResponseFull(
        UUID id,
        String nomeRestaurante,
        String tipoCozinha,
        String horarioFuncionamento,
        String nomeDono,
        EnderecoResponse enderecoResponse
) {
}
