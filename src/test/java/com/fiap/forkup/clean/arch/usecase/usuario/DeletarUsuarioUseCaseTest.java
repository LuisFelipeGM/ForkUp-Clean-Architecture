package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.exception.UsuarioComRestauranteVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.DeletarUsuarioUseCase;
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
@DisplayName("Testes unitários para o caso de uso DeletarUsuarioUseCase")
public class DeletarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Test
    @DisplayName("Deve deletar um Usuario com sucesso")
    void deveDeletarUsuarioComSucesso() {
        when(usuarioGateway.existsUsuario(any(UUID.class))).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(any(UUID.class))).thenReturn(false);

        deletarUsuarioUseCase.execute(UUID.randomUUID());

        verify(usuarioGateway, times(1)).existsUsuario(any(UUID.class));
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(any(UUID.class));
        verify(usuarioGateway, times(1)).deletar(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar um Usuario que não existe")
    void deveLancarExcecaoUsuarioNaoExiste() {
        when(usuarioGateway.existsUsuario(any(UUID.class))).thenReturn(false);

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> deletarUsuarioUseCase.execute(UUID.randomUUID()));

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuario(any(UUID.class));
        verify(usuarioGateway, never()).deletar(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar um Usuario que possui restaurantes associados")
    void deveLancarExcecaoUsuarioComRestauranteVinculado() {
        when(usuarioGateway.existsUsuario(any(UUID.class))).thenReturn(true);
        when(usuarioGateway.existsRestauranteVinculadoUsuario(any(UUID.class))).thenReturn(true);

        UsuarioComRestauranteVinculadoException exception = assertThrows(UsuarioComRestauranteVinculadoException.class, () -> deletarUsuarioUseCase.execute(UUID.randomUUID()));

        assertEquals("Não é possível excluir o usuário pois existem restaurantes vinculados a ele.", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuario(any(UUID.class));
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(any(UUID.class));
        verify(usuarioGateway, never()).deletar(any(UUID.class));
    }

}
