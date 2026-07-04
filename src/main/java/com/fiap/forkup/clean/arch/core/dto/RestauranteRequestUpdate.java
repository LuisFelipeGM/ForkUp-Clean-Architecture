package com.fiap.forkup.clean.arch.core.dto;

public record RestauranteRequestUpdate(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        EnderecoRequest endereco
) { }