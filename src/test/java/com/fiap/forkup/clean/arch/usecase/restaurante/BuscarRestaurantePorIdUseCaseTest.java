package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.BuscarRestaurantePorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso BuscarRestaurantePorIdUseCase")
public class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Test
    @DisplayName("Deve retornar um Restaurante por ID com sucesso")
    void deveRetornarRestaurantePorIdComSucesso() {
        UUID id = UUID.randomUUID();
        Restaurante restaurante = criarRestaurante(id);

        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.of(restaurante));
        when(restauranteGateway.nomeDonoVinculadoRestaurante(id)).thenReturn("João Silva");

        Restaurante retorno = buscarRestaurantePorIdUseCase.execute(id);

        assertNotNull(retorno);
        assertEquals(restaurante.getId(), retorno.getId());
        assertEquals(restaurante.getNome(), retorno.getNome());
        assertEquals("João Silva", retorno.getNomeDono());
        verify(restauranteGateway, times(1)).buscarPorId(id);
        verify(restauranteGateway, times(1)).nomeDonoVinculadoRestaurante(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar um Restaurante por ID")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> buscarRestaurantePorIdUseCase.execute(id));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorId(id);
        verify(restauranteGateway, never()).nomeDonoVinculadoRestaurante(any());
    }

    private Restaurante criarRestaurante(UUID id) {
        return new Restaurante(
                id,
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

}
