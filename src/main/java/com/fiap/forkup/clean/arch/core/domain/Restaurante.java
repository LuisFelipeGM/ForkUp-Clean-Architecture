package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.ItemCardapioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.exception.RestauranteInvalidoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Restaurante {

    private UUID id;
    private String nome;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Endereco endereco;
    private UUID gerente;
    private List<ItemCardapio> cardapio;

    public Restaurante(UUID id, String nome, String tipoCozinha, String horarioFuncionamento, Endereco endereco, UUID gerente) {
        validarNome(nome);
        validarTipoCozinha(tipoCozinha);
        validarHorarioFuncionamento(horarioFuncionamento);
        validarEndereco(endereco);

        this.id = id;
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.endereco = endereco;
        this.gerente = gerente;
        this.cardapio = new ArrayList<>();
    }

    public void adicionarItemCardapio(ItemCardapio item) {
        validarItemCardapio(item);
        this.cardapio.add(item);
    }

    public void atualizarItemCardapio(UUID itemId, ItemCardapio novoItem) {

        ItemCardapio item = cardapio.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException("Item do cardápio não encontrado."));

        item.atualizar(novoItem);
    }

    public void removerItemCardapio(UUID itemCardapioId) {
        ItemCardapio item = cardapio.stream()
                .filter(i -> i.getId().equals(itemCardapioId))
                .findFirst()
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException("Item do cardápio não encontrado."));

        this.cardapio.remove(item);
    }

    public void alterarRestaurante(String nome, String tipoCozinha, String horarioFuncionamento, Endereco endereco) {
        validarNome(nome);
        validarTipoCozinha(tipoCozinha);
        validarHorarioFuncionamento(horarioFuncionamento);
        validarEndereco(endereco);

        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.endereco = endereco;
    }

    public void alterarGerente(UUID idGerente) {
        this.gerente = idGerente;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new RestauranteInvalidoException("Nome do restaurante é obrigatório.");

        if (nome.trim().length() < 2)
            throw new RestauranteInvalidoException("Nome do restaurante deve ter ao menos 2 caracteres.");
    }

    private void validarTipoCozinha(String tipoCozinha) {
        if (tipoCozinha == null || tipoCozinha.isBlank())
            throw new RestauranteInvalidoException("Tipo de cozinha é obrigatório.");
    }

    private void validarHorarioFuncionamento(String horarioFuncionamento) {
        if (horarioFuncionamento == null || horarioFuncionamento.isBlank())
            throw new RestauranteInvalidoException("Horário de funcionamento é obrigatório.");
    }

    private void validarEndereco(Endereco endereco) {
        if (endereco == null)
            throw new RestauranteInvalidoException("Endereço é obrigatório.");
    }

    private void validarItemCardapio(ItemCardapio item) {
        if (item == null)
            throw new RestauranteInvalidoException("Item do cardápio é obrigatório.");
    }

}
