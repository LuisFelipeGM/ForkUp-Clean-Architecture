package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.ItemCardapioInvalidoException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ItemCardapio {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean apenasRestaurante;
    private String pathFoto;

    public ItemCardapio(UUID id, String nome, String descricao, BigDecimal preco, Boolean apenasRestaurante, String pathFoto) {
        validarNome(nome);
        validarDescricao(descricao);
        validarPreco(preco);
        validarApenasRestaurante(apenasRestaurante);
        validarPathFoto(pathFoto);

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.apenasRestaurante = apenasRestaurante;
        this.pathFoto = pathFoto;
    }

    public void atualizar(ItemCardapio novoItem) {
        this.nome = novoItem.getNome();
        this.descricao = novoItem.getDescricao();
        this.preco = novoItem.getPreco();
        this.apenasRestaurante = novoItem.getApenasRestaurante();
        this.pathFoto = novoItem.getPathFoto();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new ItemCardapioInvalidoException("Nome do item do cardápio não pode ser nulo ou vazio.");

        if (nome.trim().length() < 2)
            throw new ItemCardapioInvalidoException("Nome do item do cardápio deve ter ao menos 2 caracteres.");
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank())
            throw new ItemCardapioInvalidoException("Descrição do item do cardápio não pode ser vazia.");
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null)
            throw new ItemCardapioInvalidoException("Preço do item do cardápio é obrigatório.");

        if (preco.compareTo(BigDecimal.ZERO) <= 0)
            throw new ItemCardapioInvalidoException("Preço do item do cardápio deve ser maior que zero.");
    }

    private void validarApenasRestaurante(Boolean apenasRestaurante) {
        if (apenasRestaurante == null)
            throw new ItemCardapioInvalidoException("É necessário informar se o item é exclusivo do restaurante.");
    }

    private void validarPathFoto(String pathFoto) {
        if (pathFoto == null || pathFoto.isBlank())
            throw new ItemCardapioInvalidoException("Foto do item do cardápio é obrigatório.");
    }

}
