package com.fiap.forkup.clean.arch.core.dto;

import java.util.UUID;

public record RestauranteRequestCreate(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID gerenteId,
        EnderecoRequest endereco
) { }