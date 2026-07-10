package com.fiap.forkup.clean.arch.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.exception.DonoJaVinculadoRestauranteException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioDonoNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.CriarRestauranteUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso CriarRestauranteUseCase")
public class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @Test
    @DisplayName("Deve criar um Restaurante com sucesso")
    void deveCriarRestauranteComSucesso() {
        Restaurante restaurante = criarRestaurante();

        when(usuarioGateway.existsUsuarioDono(restaurante.getDono())).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(restaurante.getDono())).thenReturn(false);
        when(restauranteGateway.criar(any())).thenReturn(restaurante.getId());

        UUID idCriado = criarRestauranteUseCase.execute(restaurante);

        assertNotNull(idCriado);
        assertEquals(restaurante.getId(), idCriado);
        verify(usuarioGateway, times(1)).existsUsuarioDono(restaurante.getDono());
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(restaurante.getDono());
        verify(restauranteGateway, times(1)).criar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o dono não existir")
    void deveLancarExcecaoDonoNaoEncontrado() {
        Restaurante restaurante = criarRestaurante();

        when(usuarioGateway.existsUsuarioDono(restaurante.getDono())).thenReturn(false);

        UsuarioDonoNaoEncontradoException exception = assertThrows(UsuarioDonoNaoEncontradoException.class, () -> criarRestauranteUseCase.execute(restaurante));

        assertEquals("Dono do Restaurante não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuarioDono(restaurante.getDono());
        verify(restauranteGateway, never()).criar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o dono já possui um restaurante vinculado")
    void deveLancarExcecaoDonoJaVinculado() {
        Restaurante restaurante = criarRestaurante();

        when(usuarioGateway.existsUsuarioDono(restaurante.getDono())).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(restaurante.getDono())).thenReturn(true);

        DonoJaVinculadoRestauranteException exception = assertThrows(DonoJaVinculadoRestauranteException.class, () -> criarRestauranteUseCase.execute(restaurante));

        assertEquals("Não é possível criar o restaurante pois o dono já está vinculado a outro restaurante.", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuarioDono(restaurante.getDono());
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(restaurante.getDono());
        verify(restauranteGateway, never()).criar(any());
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
