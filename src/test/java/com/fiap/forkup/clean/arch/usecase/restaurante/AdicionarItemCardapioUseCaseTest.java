package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.AdicionarItemCardapioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso AdicionarItemCardapioUseCase")
public class AdicionarItemCardapioUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private AdicionarItemCardapioUseCase adicionarItemCardapioUseCase;

    @Test
    @DisplayName("Deve adicionar um item ao cardápio com sucesso")
    void deveAdicionarItemComSucesso() {
        Restaurante restaurante = criarRestaurante();
        ItemCardapio itemCardapio = criarItemCardapio();

        when(restauranteGateway.buscarPorIdComCardapio(restaurante.getId())).thenReturn(Optional.of(restaurante));

        ItemCardapio retorno = adicionarItemCardapioUseCase.execute(restaurante.getId(), itemCardapio);

        assertNotNull(retorno);
        assertEquals(itemCardapio.getNome(), retorno.getNome());
        verify(restauranteGateway, times(1)).buscarPorIdComCardapio(restaurante.getId());
        verify(restauranteGateway, times(1)).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Restaurante não for encontrado")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        UUID idRestaurante = UUID.randomUUID();
        ItemCardapio itemCardapio = criarItemCardapio();

        when(restauranteGateway.buscarPorIdComCardapio(idRestaurante)).thenReturn(Optional.empty());

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> adicionarItemCardapioUseCase.execute(idRestaurante, itemCardapio));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorIdComCardapio(idRestaurante);
        verify(restauranteGateway, never()).atualizar(any());
    }

    private Restaurante criarRestaurante() {
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

    private Endereco criarEndereco() {
        return new Endereco(
                UUID.randomUUID(),
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
                "https://example.com/pizza.jpg"
        );
    }

}
