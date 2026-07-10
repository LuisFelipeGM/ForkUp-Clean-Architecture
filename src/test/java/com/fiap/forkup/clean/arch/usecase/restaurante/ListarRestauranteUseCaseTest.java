package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.ListarRestauranteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso ListarRestauranteUseCase")
public class ListarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private ListarRestauranteUseCase listarRestauranteUseCase;

    @Test
    @DisplayName("Deve listar restaurantes com sucesso")
    void deveListarRestaurantesComSucesso() {
        Pagina<Restaurante> paginaEsperada = new Pagina<>(java.util.List.of(), 0, 10, 1L, 1);

        when(restauranteGateway.listarTodos(0, 10)).thenReturn(paginaEsperada);

        Pagina<Restaurante> paginaRetornada = listarRestauranteUseCase.execute(0, 10);

        assertEquals(paginaEsperada, paginaRetornada);
        verify(restauranteGateway, times(1)).listarTodos(0, 10);
    }

}
