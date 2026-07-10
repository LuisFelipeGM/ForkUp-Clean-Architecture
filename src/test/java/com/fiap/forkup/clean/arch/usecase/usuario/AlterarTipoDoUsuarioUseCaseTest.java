package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.UsuarioComRestauranteVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.AlterarTipoDoUsuarioUseCase;
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
@DisplayName("Testes unitários para o caso de uso AlterarTipoDoUsuarioUseCase")
public class AlterarTipoDoUsuarioUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase;

    @Test
    @DisplayName("Deve alterar o tipo de um Usuario com sucesso")
    void deveAlterarTipoUsuarioComSucesso() {
        Usuario usuario = criarUsuario();
        TipoUsuario tipoUsuarioNovo = new TipoUsuario(UUID.randomUUID(), "Dono do Restaurante");

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(tipoUsuarioGateway.buscarPorId(tipoUsuarioNovo.getId())).thenReturn(Optional.of(tipoUsuarioNovo));
        when(usuarioGateway.existsRestauranteVinculadoUsuario(usuario.getId())).thenReturn(false);

        Usuario retorno = alterarTipoDoUsuarioUseCase.execute(usuario.getId(), tipoUsuarioNovo.getId());

        assertNotNull(retorno);
        assertEquals(tipoUsuarioNovo, retorno.getTipoUsuario());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuarioNovo.getId());
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(usuario.getId());
        verify(usuarioGateway, times(1)).atualizar(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Usuario não for encontrado")
    void deveLancarExcecaoUsuarioNaoEncontrado() {
        UUID idUsuario = UUID.randomUUID();
        UUID idTipoUsuario = UUID.randomUUID();

        when(usuarioGateway.buscarPorId(idUsuario)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> alterarTipoDoUsuarioUseCase.execute(idUsuario, idTipoUsuario));

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(idUsuario);
        verifyNoInteractions(tipoUsuarioGateway);
        verify(usuarioGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o TipoUsuario não for encontrado")
    void deveLancarExcecaoTipoUsuarioNaoEncontrado() {
        Usuario usuario = criarUsuario();
        UUID idTipoUsuario = UUID.randomUUID();

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(tipoUsuarioGateway.buscarPorId(idTipoUsuario)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> alterarTipoDoUsuarioUseCase.execute(usuario.getId(), idTipoUsuario));

        assertEquals("Tipo de usuário não encontrado", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(idTipoUsuario);
        verify(usuarioGateway, never()).atualizar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Usuario possuir restaurantes vinculados")
    void deveLancarExcecaoUsuarioComRestauranteVinculado() {
        Usuario usuario = criarUsuario();
        TipoUsuario tipoUsuarioNovo = new TipoUsuario(UUID.randomUUID(), "Dono do Restaurante");

        when(usuarioGateway.buscarPorId(usuario.getId())).thenReturn(Optional.of(usuario));
        when(tipoUsuarioGateway.buscarPorId(tipoUsuarioNovo.getId())).thenReturn(Optional.of(tipoUsuarioNovo));
        when(usuarioGateway.existsRestauranteVinculadoUsuario(usuario.getId())).thenReturn(true);

        UsuarioComRestauranteVinculadoException exception = assertThrows(UsuarioComRestauranteVinculadoException.class, () -> alterarTipoDoUsuarioUseCase.execute(usuario.getId(), tipoUsuarioNovo.getId()));

        assertEquals("Não é possível alterar o tipo do usuário pois existem restaurantes vinculados a ele.", exception.getMessage());
        verify(usuarioGateway, times(1)).buscarPorId(usuario.getId());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuarioNovo.getId());
        verify(usuarioGateway, times(1)).existsRestauranteVinculadoUsuario(usuario.getId());
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

}
