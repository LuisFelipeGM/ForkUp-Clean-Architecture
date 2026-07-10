package com.fiap.forkup.clean.arch.domain;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.exception.ItemCardapioInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe ItemCardapio")
public class ItemCardapioTest {

    @Test
    @DisplayName("Deve criar um ItemCardapio válido")
    void deveCriarItemCardapioValido() {
        UUID id = UUID.randomUUID();
        String nome = "Pizza Margherita";
        String descricao = "Mussarela, tomate e manjericão";
        BigDecimal preco = new BigDecimal("29.90");
        Boolean apenasRestaurante = false;
        String pathFoto = "https://example.com/pizza.jpg";

        ItemCardapio itemCardapio = new ItemCardapio(id, nome, descricao, preco, apenasRestaurante, pathFoto);

        assertNotNull(itemCardapio);
        assertEquals(id, itemCardapio.getId());
        assertEquals(nome, itemCardapio.getNome());
        assertEquals(descricao, itemCardapio.getDescricao());
        assertEquals(preco, itemCardapio.getPreco());
        assertEquals(apenasRestaurante, itemCardapio.getApenasRestaurante());
        assertEquals(pathFoto, itemCardapio.getPathFoto());
    }

    @Test
    @DisplayName("Deve atualizar um ItemCardapio com sucesso")
    void deveAtualizarItemCardapioComSucesso() {
        ItemCardapio item = criarItemCardapioValido();
        ItemCardapio novoItem = new ItemCardapio(
                UUID.randomUUID(),
                "Pizza Calabresa",
                "Calabresa, mussarela e cebola",
                new BigDecimal("34.90"),
                true,
                "https://example.com/pizza-calabresa.jpg"
        );

        item.atualizar(novoItem);

        assertEquals(novoItem.getNome(), item.getNome());
        assertEquals(novoItem.getDescricao(), item.getDescricao());
        assertEquals(novoItem.getPreco(), item.getPreco());
        assertEquals(novoItem.getApenasRestaurante(), item.getApenasRestaurante());
        assertEquals(novoItem.getPathFoto(), item.getPathFoto());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void deveLancarExcecaoNomeNulo() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), null, "Descrição", new BigDecimal("10.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for vazio")
    void deveLancarExcecaoNomeVazio() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "", "Descrição", new BigDecimal("10.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome tiver menos de 2 caracteres")
    void deveLancarExcecaoNomeMenosDe2Caracteres() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "A", "Descrição", new BigDecimal("10.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula")
    void deveLancarExcecaoDescricaoNula() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", null, new BigDecimal("10.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for vazia")
    void deveLancarExcecaoDescricaoVazia() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "", new BigDecimal("10.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for nulo")
    void deveLancarExcecaoPrecoNulo() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", null, false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for zero")
    void deveLancarExcecaoPrecoZero() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", BigDecimal.ZERO, false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for negativo")
    void deveLancarExcecaoPrecoNegativo() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", new BigDecimal("-1.00"), false, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando apenasRestaurante for nulo")
    void deveLancarExcecaoApenasRestauranteNulo() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", new BigDecimal("10.00"), null, "foto.jpg");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a foto for nula")
    void deveLancarExcecaoFotoNula() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", new BigDecimal("10.00"), false, null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a foto for vazia")
    void deveLancarExcecaoFotoVazia() {
        assertThrows(ItemCardapioInvalidoException.class, () -> {
            new ItemCardapio(UUID.randomUUID(), "Pizza", "Descrição", new BigDecimal("10.00"), false, "");
        });
    }

    private ItemCardapio criarItemCardapioValido() {
        return new ItemCardapio(
                UUID.randomUUID(),
                "Pizza Margherita",
                "Mussarela, tomate e manjericão",
                new BigDecimal("29.90"),
                false,
                "https://example.com/pizza.jpg"
        );
    }

}
