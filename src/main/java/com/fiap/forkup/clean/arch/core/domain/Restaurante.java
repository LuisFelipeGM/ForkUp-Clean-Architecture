package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.RestauranteInvalidoException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurante {

    private UUID id;
    private String nome;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Endereco endereco;
    private UUID dono;
    private List<ItemCardapio> cardapio;

    public Restaurante(UUID id, String nome, String tipoCozinha, String horarioFuncionamento, Endereco endereco, UUID dono) {
        validarNome(nome);
        validarTipoCozinha(tipoCozinha);
        validarHorarioFuncionamento(horarioFuncionamento);
        validarEndereco(endereco);

        this.id = id;
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.endereco = endereco;
        this.dono = dono;
        this.cardapio = new ArrayList<>();
    }

    public void adicionarItemCardapio(ItemCardapio item) {
        validarItemCardapio(item);
        this.cardapio.add(item);
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new RestauranteInvalidoException("Nome do restaurante é obrigatório.");

        if (nome.trim().length() < 2)
            throw new RestauranteInvalidoException("Nome do restaurante deve ter ao menos 2 caracteres.");
    }

    private static void validarTipoCozinha(String tipoCozinha) {
        if (tipoCozinha == null || tipoCozinha.isBlank())
            throw new RestauranteInvalidoException("Tipo de cozinha é obrigatório.");
    }

    private static void validarHorarioFuncionamento(String horarioFuncionamento) {
        if (horarioFuncionamento == null || horarioFuncionamento.isBlank())
            throw new RestauranteInvalidoException("Horário de funcionamento é obrigatório.");
    }

    private static void validarEndereco(Endereco endereco) {
        if (endereco == null)
            throw new RestauranteInvalidoException("Endereço é obrigatório.");
    }

    private static void validarItemCardapio(ItemCardapio item) {
        if (item == null)
            throw new RestauranteInvalidoException("Item do cardápio é obrigatório.");
    }

}
