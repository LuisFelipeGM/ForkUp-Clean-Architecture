package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.DonoJaVinculadoRestauranteException;
import com.fiap.forkup.clean.arch.core.exception.RestauranteNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioDonoNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.AlterarDonoRestauranteUseCase;
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
@DisplayName("Testes unitários para o caso de uso AlterarDonoRestauranteUseCase")
public class AlterarDonoRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AlterarDonoRestauranteUseCase alterarDonoRestauranteUseCase;

    @Test
    @DisplayName("Deve alterar o dono de um Restaurante com sucesso")
    void deveAlterarDonoRestauranteComSucesso() {
        Restaurante restaurante = criarRestaurante();
        UUID novoDonoId = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(usuarioGateway.existsUsuarioDono(novoDonoId)).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(novoDonoId)).thenReturn(false);
        when(restauranteGateway.nomeDonoVinculadoRestaurante(restaurante.getId())).thenReturn("Maria Silva");

        Restaurante retorno = alterarDonoRestauranteUseCase.execute(restaurante.getId(), novoDonoId);

        assertNotNull(retorno);
        assertEquals(novoDonoId, retorno.getDono());
        assertEquals("Maria Silva", retorno.getNomeDono());
        verify(restauranteGateway, times(1)).buscarPorId(restaurante.getId());
        verify(usuarioGateway, times(1)).existsUsuarioDono(novoDonoId);
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(novoDonoId);
        verify(restauranteGateway, times(1)).atualizar(any());
        verify(restauranteGateway, times(1)).nomeDonoVinculadoRestaurante(restaurante.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Restaurante não for encontrado")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        UUID idRestaurante = UUID.randomUUID();
        UUID idDono = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(idRestaurante)).thenReturn(Optional.empty());

        RestauranteNaoEncontradoException exception = assertThrows(RestauranteNaoEncontradoException.class, () -> alterarDonoRestauranteUseCase.execute(idRestaurante, idDono));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorId(idRestaurante);
        verifyNoInteractions(usuarioGateway);
        verify(restauranteGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo dono não existir")
    void deveLancarExcecaoNovoDonoNaoEncontrado() {
        Restaurante restaurante = criarRestaurante();
        UUID novoDonoId = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(usuarioGateway.existsUsuarioDono(novoDonoId)).thenReturn(false);

        UsuarioDonoNaoEncontradoException exception = assertThrows(UsuarioDonoNaoEncontradoException.class, () -> alterarDonoRestauranteUseCase.execute(restaurante.getId(), novoDonoId));

        assertEquals("Dono do Restaurante não encontrado", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorId(restaurante.getId());
        verify(usuarioGateway, times(1)).existsUsuarioDono(novoDonoId);
        verify(restauranteGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo dono já possui um restaurante vinculado")
    void deveLancarExcecaoNovoDonoJaVinculado() {
        Restaurante restaurante = criarRestaurante();
        UUID novoDonoId = UUID.randomUUID();

        when(restauranteGateway.buscarPorId(restaurante.getId())).thenReturn(Optional.of(restaurante));
        when(usuarioGateway.existsUsuarioDono(novoDonoId)).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(novoDonoId)).thenReturn(true);

        DonoJaVinculadoRestauranteException exception = assertThrows(DonoJaVinculadoRestauranteException.class, () -> alterarDonoRestauranteUseCase.execute(restaurante.getId(), novoDonoId));

        assertEquals("Dono do Restaurante já está vinculado a outro restaurante.", exception.getMessage());
        verify(restauranteGateway, times(1)).buscarPorId(restaurante.getId());
        verify(usuarioGateway, times(1)).existsUsuarioDono(novoDonoId);
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(novoDonoId);
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

}
