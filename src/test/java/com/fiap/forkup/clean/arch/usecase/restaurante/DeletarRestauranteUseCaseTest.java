package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.DeletarRestauranteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso DeletarRestauranteUseCase")
public class DeletarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private DeletarRestauranteUseCase deletarRestauranteUseCase;

    @Test
    @DisplayName("Deve deletar um Restaurante com sucesso")
    void deveDeletarRestauranteComSucesso() {
        when(restauranteGateway.existsRestaurante(any(UUID.class))).thenReturn(true);

        deletarRestauranteUseCase.execute(UUID.randomUUID());

        verify(restauranteGateway, times(1)).existsRestaurante(any(UUID.class));
        verify(restauranteGateway, times(1)).deletar(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar um Restaurante que não existe")
    void deveLancarExcecaoRestauranteNaoExiste() {
        when(restauranteGateway.existsRestaurante(any(UUID.class))).thenReturn(false);

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> deletarRestauranteUseCase.execute(UUID.randomUUID()));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).existsRestaurante(any(UUID.class));
        verify(restauranteGateway, never()).deletar(any(UUID.class));
    }

}
