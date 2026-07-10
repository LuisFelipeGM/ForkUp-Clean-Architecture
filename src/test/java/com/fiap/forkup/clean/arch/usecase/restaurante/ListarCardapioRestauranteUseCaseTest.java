package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.ListarCardapioRestauranteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso ListarCardapioRestauranteUseCase")
public class ListarCardapioRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private ListarCardapioRestauranteUseCase listarCardapioRestauranteUseCase;

    @Test
    @DisplayName("Deve listar o cardápio de um Restaurante com sucesso")
    void deveListarCardapioComSucesso() {
        UUID idRestaurante = UUID.randomUUID();
        List<ItemCardapio> cardapio = List.of(criarItemCardapio());

        when(restauranteGateway.existsRestaurante(idRestaurante)).thenReturn(true);
        when(restauranteGateway.listarCardapio(idRestaurante)).thenReturn(cardapio);

        List<ItemCardapio> retorno = listarCardapioRestauranteUseCase.execute(idRestaurante);

        assertNotNull(retorno);
        assertEquals(cardapio.size(), retorno.size());
        assertEquals(cardapio.getFirst().getNome(), retorno.getFirst().getNome());
        verify(restauranteGateway, times(1)).existsRestaurante(idRestaurante);
        verify(restauranteGateway, times(1)).listarCardapio(idRestaurante);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Restaurante não existir")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        UUID idRestaurante = UUID.randomUUID();

        when(restauranteGateway.existsRestaurante(idRestaurante)).thenReturn(false);

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> listarCardapioRestauranteUseCase.execute(idRestaurante));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).existsRestaurante(idRestaurante);
        verify(restauranteGateway, never()).listarCardapio(any());
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
