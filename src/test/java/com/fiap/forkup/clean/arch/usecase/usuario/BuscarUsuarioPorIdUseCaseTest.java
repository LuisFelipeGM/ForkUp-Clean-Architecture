package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso BuscarUsuarioPorIdUseCase")
public class BuscarUsuarioPorIdUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Test
    @DisplayName("Deve retornar um Usuario por ID com sucesso")
    void deveRetornarUsuarioPorIdComSucesso() {
        UUID id = UUID.randomUUID();
        Usuario usuario = criarUsuario(id);

        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.of(usuario));

        Usuario retorno = buscarUsuarioPorIdUseCase.execute(id);

        assertNotNull(retorno);
        assertEquals(usuario.getId(), retorno.getId());
        assertEquals(usuario.getNome(), retorno.getNome());
        assertEquals(usuario.getEmail(), retorno.getEmail());
        verify(usuarioGateway, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar um Usuario por ID")
    void deveLancarExcecaoUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuarioPorIdUseCase.execute(id));

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(id);
    }

    private Usuario criarUsuario(UUID id) {
        return new Usuario(
                id,
                "Luís Felipe",
                "luis.felipe@example.com",
                "LuisFelipeGM",
                "SenhaForte123@",
                new TipoUsuario(UUID.randomUUID(), "Cliente"),
                criarEndereco()
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
