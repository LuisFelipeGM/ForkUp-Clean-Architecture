package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.AtualizarUsuarioInput;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.AlterarUsuarioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso AlterarUsuarioUseCase")
public class AlterarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AlterarUsuarioUseCase alterarUsuarioUseCase;

    @Test
    @DisplayName("Deve alterar um Usuario com sucesso")
    void deveAlterarUsuarioComSucesso() {
        Usuario usuario = criarUsuario();
        AtualizarUsuarioInput input = new AtualizarUsuarioInput(
                "Marcos Almeida",
                "marcos.almeida@gmail.com",
                "MarcosAL",
                new Endereco(UUID.randomUUID(), "Rua B", "456", "Apto 202", "Rio de Janeiro", "98765-432")
        );

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioGateway.existsUsuarioComEsteLoginAndIdNot(input.login(), usuario.getId())).thenReturn(false);
        when(usuarioGateway.existsUsuarioComEsteEmailAndIdNot(input.email(), usuario.getId())).thenReturn(false);

        Usuario retorno = alterarUsuarioUseCase.execute(usuario.getId(), input);

        assertNotNull(retorno);
        assertEquals(input.nome(), retorno.getNome());
        assertEquals(input.email(), retorno.getEmail());
        assertEquals(input.login(), retorno.getLogin());
        assertEquals(input.endereco(), retorno.getEndereco());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(usuarioGateway, times(1)).atualizar(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Usuario não for encontrado")
    void deveLancarExcecaoUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();
        AtualizarUsuarioInput input = criarInput();

        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> alterarUsuarioUseCase.execute(id, input));

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(id);
        verify(usuarioGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login já estiver em uso")
    void deveLancarExcecaoLoginJaCadastrado() {
        Usuario usuario = criarUsuario();
        AtualizarUsuarioInput input = criarInput();

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioGateway.existsUsuarioComEsteLoginAndIdNot(input.login(), usuario.getId())).thenReturn(true);

        LoginUsuarioJaCadastradoException exception = assertThrows(LoginUsuarioJaCadastradoException.class, () -> alterarUsuarioUseCase.execute(usuario.getId(), input));

        assertEquals("Já existe um usuário com este login", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLoginAndIdNot(input.login(), usuario.getId());
        verify(usuarioGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email já estiver em uso")
    void deveLancarExcecaoEmailJaCadastrado() {
        Usuario usuario = criarUsuario();
        AtualizarUsuarioInput input = criarInput();

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioGateway.existsUsuarioComEsteLoginAndIdNot(input.login(), usuario.getId())).thenReturn(false);
        when(usuarioGateway.existsUsuarioComEsteEmailAndIdNot(input.email(), usuario.getId())).thenReturn(true);

        EmailUsuarioJaCadastradoException exception = assertThrows(EmailUsuarioJaCadastradoException.class, () -> alterarUsuarioUseCase.execute(usuario.getId(), input));

        assertEquals("Já existe um usuário com este email", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLoginAndIdNot(input.login(), usuario.getId());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteEmailAndIdNot(input.email(), usuario.getId());
        verify(usuarioGateway, never()).atualizar(any());
    }

    private Usuario criarUsuario() {
        return new Usuario(
                UUID.randomUUID(),
                "Luís Felipe",
                "luis.felipe@example.com",
                "LuisFelipeGM",
                "SenhaForte123@",
                new TipoUsuario(UUID.randomUUID(), "Cliente"),
                new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", "São Paulo", "12345-678")
        );
    }

    private AtualizarUsuarioInput criarInput() {
        return new AtualizarUsuarioInput(
                "Marcos Almeida",
                "marcos.almeida@gmail.com",
                "MarcosAL",
                new Endereco(UUID.randomUUID(), "Rua B", "456", "Apto 202", "Rio de Janeiro", "98765-432")
        );
    }

}
