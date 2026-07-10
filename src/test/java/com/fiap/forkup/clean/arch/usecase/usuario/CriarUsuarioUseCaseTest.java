package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.CriarUsuarioUseCase;
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
@DisplayName("Testes unitários para o caso de uso CriarUsuarioUseCase")
public class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private CriarUsuarioUseCase criarUsuarioUseCase;

    @Test
    @DisplayName("Deve criar um Usuario com sucesso")
    void deveCriarUsuarioComSucesso() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");
        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                "Luís Felipe",
                "luis.felipe@example.com",
                "LuisFelipeGM",
                "SenhaForte123@",
                tipoUsuario,
                criarEndereco()
        );

        when(usuarioGateway.existsUsuarioComEsteLogin(usuario.getLogin())).thenReturn(false);
        when(usuarioGateway.existsUsuarioComEsteEmail(usuario.getEmail())).thenReturn(false);
        when(tipoUsuarioGateway.buscarPorId(tipoUsuario.getId())).thenReturn(Optional.of(tipoUsuario));
        when(usuarioGateway.criar(any())).thenReturn(usuario.getId());

        UUID idCriado = criarUsuarioUseCase.execute(usuario);

        assertNotNull(idCriado);
        assertEquals(tipoUsuario, usuario.getTipoUsuario());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLogin(usuario.getLogin());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteEmail(usuario.getEmail());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuario.getId());
        verify(usuarioGateway, times(1)).criar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um Usuario com login já existente")
    void deveLancarExcecaoLoginUsuarioJaExiste() {
        Usuario usuario = criarUsuarioNovo();

        when(usuarioGateway.existsUsuarioComEsteLogin(usuario.getLogin())).thenReturn(true);

        LoginUsuarioJaCadastradoException exception = assertThrows(LoginUsuarioJaCadastradoException.class, () -> criarUsuarioUseCase.execute(usuario));

        assertEquals("Login já cadastrado", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLogin(usuario.getLogin());
        verify(usuarioGateway, never()).criar(any());
        verifyNoInteractions(tipoUsuarioGateway);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um Usuario com email já existente")
    void deveLancarExcecaoEmailUsuarioJaExiste() {
        Usuario usuario = criarUsuarioNovo();

        when(usuarioGateway.existsUsuarioComEsteLogin(usuario.getLogin())).thenReturn(false);
        when(usuarioGateway.existsUsuarioComEsteEmail(usuario.getEmail())).thenReturn(true);

        EmailUsuarioJaCadastradoException exception = assertThrows(EmailUsuarioJaCadastradoException.class, () -> criarUsuarioUseCase.execute(usuario));

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLogin(usuario.getLogin());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteEmail(usuario.getEmail());
        verify(usuarioGateway, never()).criar(any());
        verifyNoInteractions(tipoUsuarioGateway);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de usuário não existir")
    void deveLancarExcecaoTipoUsuarioNaoEncontrado() {
        Usuario usuario = criarUsuarioNovo();

        when(usuarioGateway.existsUsuarioComEsteLogin(usuario.getLogin())).thenReturn(false);
        when(usuarioGateway.existsUsuarioComEsteEmail(usuario.getEmail())).thenReturn(false);
        when(tipoUsuarioGateway.buscarPorId(usuario.getTipoUsuario().getId())).thenReturn(Optional.empty());

        TipoUsuarioNaoEncontradoException exception = assertThrows(TipoUsuarioNaoEncontradoException.class, () -> criarUsuarioUseCase.execute(usuario));

        assertEquals("Tipo Usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteLogin(usuario.getLogin());
        verify(usuarioGateway, times(1)).existsUsuarioComEsteEmail(usuario.getEmail());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(usuario.getTipoUsuario().getId());
        verify(usuarioGateway, never()).criar(any());
    }

    private Usuario criarUsuarioNovo() {
        return new Usuario(
                UUID.randomUUID(),
                "Luís Felipe",
                "luis.felipe@example.com",
                "LuisFelipeGM",
                "SenhaForte123@",
                new TipoUsuario(UUID.randomUUID()),
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
