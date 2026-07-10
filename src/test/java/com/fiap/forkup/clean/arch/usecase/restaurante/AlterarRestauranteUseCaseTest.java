package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.AtualizarRestauranteInput;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.AlterarRestauranteUseCase;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso AlterarRestauranteUseCase")
public class AlterarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private AlterarRestauranteUseCase alterarRestauranteUseCase;

    @Test
    @DisplayName("Deve alterar um Restaurante com sucesso")
    void deveAlterarRestauranteComSucesso() {
        Restaurante restaurante = criarRestaurante();
        AtualizarRestauranteInput input = new AtualizarRestauranteInput(
                "Novo Nome",
                "Italiana",
                "10:00 - 23:00",
                new Endereco(UUID.randomUUID(), "Rua B", "456", "Sala 2", "Rio de Janeiro", "98765-432")
        );

        when(restauranteGateway.buscarPorId(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(restauranteGateway.nomeDonoVinculadoRestaurante(restaurante.getId())).thenReturn("João Silva");

        Restaurante retorno = alterarRestauranteUseCase.execute(restaurante.getId(), input);

        assertNotNull(retorno);
        assertEquals(input.nome(), retorno.getNome());
        assertEquals(input.tipoCozinha(), retorno.getTipoCozinha());
        assertEquals(input.horarioFuncionamento(), retorno.getHorarioFuncionamento());
        assertEquals("João Silva", retorno.getNomeDono());
        verify(restauranteGateway, times(1)).buscarPorId(restaurante.getId());
        verify(restauranteGateway, times(1)).atualizar(any());
        verify(restauranteGateway, times(1)).nomeDonoVinculadoRestaurante(restaurante.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Restaurante não for encontrado")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();
        AtualizarRestauranteInput input = criarInput();

        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> alterarRestauranteUseCase.execute(id, input));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorId(id);
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

    private AtualizarRestauranteInput criarInput() {
        return new AtualizarRestauranteInput(
                "Novo Nome",
                "Italiana",
                "10:00 - 23:00",
                criarEndereco()
        );
    }

}
