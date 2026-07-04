package com.fiap.forkup.clean.arch.core.dto;

public record UpdateRestauranteRequest(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        EnderecoRequest endereco
) { }