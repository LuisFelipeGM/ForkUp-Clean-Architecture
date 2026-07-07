package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.Endereco;

public record AtualizarRestauranteInput(
        String nome,
        String tipoCozinha,
        String horarioFuncionamento,
        Endereco endereco
) { }
