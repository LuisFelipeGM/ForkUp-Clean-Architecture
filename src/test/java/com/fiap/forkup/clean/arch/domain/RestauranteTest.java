package com.fiap.forkup.clean.arch.domain;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.ItemCardapioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.exception.RestauranteInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe Restaurante")
public class RestauranteTest {

    @Test
    @DisplayName("Deve criar um Restaurante válido")
    void deveCriarRestauranteValido() {
        UUID id = UUID.randomUUID();
        String nome = "Sabor Brasileiro";
        String tipoCozinha = "Brasileira";
        String horarioFuncionamento = "08:00 - 22:00";
        Endereco endereco = criarEndereco();
        UUID dono = UUID.randomUUID();
        List<ItemCardapio> cardapio = new ArrayList<>(List.of(criarItemCardapio()));

        Restaurante restaurante = new Restaurante(id, nome, tipoCozinha, horarioFuncionamento, endereco, dono, cardapio);

        assertNotNull(restaurante);
        assertEquals(id, restaurante.getId());
        assertEquals(nome, restaurante.getNome());
        assertEquals(tipoCozinha, restaurante.getTipoCozinha());
        assertEquals(horarioFuncionamento, restaurante.getHorarioFuncionamento());
        assertEquals(endereco, restaurante.getEndereco());
        assertEquals(dono, restaurante.getDono());
        assertEquals(cardapio, restaurante.getCardapio());
    }

    @Test
    @DisplayName("Deve alterar os dados de um Restaurante com sucesso")
    void deveAlterarRestauranteComSucesso() {
        Restaurante restaurante = criarRestauranteCompleto();
        Endereco novoEndereco = new Endereco(UUID.randomUUID(), "Rua B", "456", "Sala 2", "Rio de Janeiro", "98765-432");

        restaurante.alterarRestaurante("Novo Nome", "Italiana", "10:00 - 23:00", novoEndereco);

        assertEquals("Novo Nome", restaurante.getNome());
        assertEquals("Italiana", restaurante.getTipoCozinha());
        assertEquals("10:00 - 23:00", restaurante.getHorarioFuncionamento());
        assertEquals(novoEndereco, restaurante.getEndereco());
    }

    @Test
    @DisplayName("Deve alterar o dono de um Restaurante com sucesso")
    void deveAlterarDonoRestauranteComSucesso() {
        Restaurante restaurante = criarRestauranteCompleto();
        UUID novoDono = UUID.randomUUID();

        restaurante.alterarDono(novoDono);

        assertEquals(novoDono, restaurante.getDono());
    }

    @Test
    @DisplayName("Deve adicionar um item ao cardápio com sucesso")
    void deveAdicionarItemAoCardapioComSucesso() {
        Restaurante restaurante = criarRestauranteSemCardapio();
        ItemCardapio item = new ItemCardapio(UUID.randomUUID(), "Lasanha", "Lasanha à bolonhesa", new BigDecimal("39.90"), false, "foto.jpg");

        restaurante.adicionarItemCardapio(item);

        assertTrue(restaurante.getCardapio().contains(item));
        assertEquals(1, restaurante.getCardapio().size());
    }

    @Test
    @DisplayName("Deve atualizar um item do cardápio com sucesso")
    void deveAtualizarItemCardapioComSucesso() {
        ItemCardapio item = criarItemCardapio();
        Restaurante restaurante = criarRestauranteComCardapio(item);

        ItemCardapio itemNovo = new ItemCardapio(
                UUID.randomUUID(),
                "Pizza Calabresa",
                "Calabresa, mussarela e cebola",
                new BigDecimal("34.90"),
                true,
                "foto-nova.jpg"
        );

        restaurante.atualizarItemCardapio(item.getId(), itemNovo);

        ItemCardapio itemAtualizado = restaurante.getCardapio().get(0);
        assertEquals("Pizza Calabresa", itemAtualizado.getNome());
        assertEquals("Calabresa, mussarela e cebola", itemAtualizado.getDescricao());
        assertEquals(new BigDecimal("34.90"), itemAtualizado.getPreco());
        assertEquals(true, itemAtualizado.getApenasRestaurante());
        assertEquals("foto-nova.jpg", itemAtualizado.getPathFoto());
    }

    @Test
    @DisplayName("Deve remover um item do cardápio com sucesso")
    void deveRemoverItemCardapioComSucesso() {
        ItemCardapio item = criarItemCardapio();
        Restaurante restaurante = criarRestauranteComCardapio(item);

        restaurante.removerItemCardapio(item.getId());

        assertTrue(restaurante.getCardapio().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void deveLancarExcecaoNomeNulo() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), null, "Brasileira", "08:00 - 22:00", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for vazio")
    void deveLancarExcecaoNomeVazio() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "", "Brasileira", "08:00 - 22:00", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome tiver menos de 2 caracteres")
    void deveLancarExcecaoNomeMenosDe2Caracteres() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "A", "Brasileira", "08:00 - 22:00", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de cozinha for nulo")
    void deveLancarExcecaoTipoCozinhaNulo() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "Sabor", null, "08:00 - 22:00", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de cozinha for vazio")
    void deveLancarExcecaoTipoCozinhaVazio() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "Sabor", "", "08:00 - 22:00", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário de funcionamento for nulo")
    void deveLancarExcecaoHorarioFuncionamentoNulo() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "Sabor", "Brasileira", null, criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário de funcionamento for vazio")
    void deveLancarExcecaoHorarioFuncionamentoVazio() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "Sabor", "Brasileira", "", criarEndereco(), UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço for nulo")
    void deveLancarExcecaoEnderecoNulo() {
        assertThrows(RestauranteInvalidoException.class, () -> {
            new Restaurante(UUID.randomUUID(), "Sabor", "Brasileira", "08:00 - 22:00", null, UUID.randomUUID(), new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando adicionar item nulo")
    void deveLancarExcecaoAoAdicionarItemNulo() {
        Restaurante restaurante = criarRestauranteCompleto();

        assertThrows(RestauranteInvalidoException.class, () -> restaurante.adicionarItemCardapio(null));
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar item inexistente")
    void deveLancarExcecaoAoAtualizarItemInexistente() {
        Restaurante restaurante = criarRestauranteCompleto();

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> {
            restaurante.atualizarItemCardapio(UUID.randomUUID(), criarItemCardapio());
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando remover item inexistente")
    void deveLancarExcecaoAoRemoverItemInexistente() {
        Restaurante restaurante = criarRestauranteCompleto();

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> {
            restaurante.removerItemCardapio(UUID.randomUUID());
        });
    }

    private Endereco criarEndereco() {
        return new Endereco(
                java.util.UUID.randomUUID(),
                "Rua A",
                "123",
                "Apto 101",
                "São Paulo",
                "12345-678"
        );
    }

    private ItemCardapio criarItemCardapio() {
        return new ItemCardapio(
                UUID.randomUUID(),
                "Pizza Margherita",
                "Mussarela, tomate e manjericão",
                new BigDecimal("29.90"),
                false,
                "foto.jpg"
        );
    }

    private Restaurante criarRestauranteCompleto() {
        return criarRestauranteComCardapio(criarItemCardapio());
    }
    private Restaurante criarRestauranteSemCardapio() {
        return new Restaurante(
                UUID.randomUUID(),
                "Sabor Brasileiro",
                "Brasileira",
                "08:00 - 22:00",
                criarEndereco(),
                UUID.randomUUID(),
                new ArrayList<>()
        );
    }

    private Restaurante criarRestauranteComCardapio(ItemCardapio itemCardapio) {
        List<ItemCardapio> cardapio = new ArrayList<>();
        cardapio.add(itemCardapio);

        return new Restaurante(
                UUID.randomUUID(),
                "Sabor Brasileiro",
                "Brasileira",
                "08:00 - 22:00",
                criarEndereco(),
                UUID.randomUUID(),
                cardapio
        );
    }

}
