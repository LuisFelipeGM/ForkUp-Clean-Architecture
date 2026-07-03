package com.fiap.forkup.clean.arch.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioComUsuarioVinculadoException;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.DeletarTipoUsuarioUseCase;
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
@DisplayName("Testes unitários para o caso de uso DeletarTipoUsuarioUseCase")
public class DeletarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    @Test
    @DisplayName("Deve deletar um TipoUsuario com sucesso")
    void deveDeletarTipoUsuarioComSucesso() {
        when(tipoUsuarioGateway.existsTipoUsuario(any(UUID.class))).thenReturn(true);
        when(tipoUsuarioGateway.existsUsuarioComEsteTipo(any(UUID.class))).thenReturn(false);

        deletarTipoUsuarioUseCase.executar(UUID.randomUUID());

        verify(tipoUsuarioGateway, times(1)).deletar(any(UUID.class));
        verify(tipoUsuarioGateway, times(1)).existsTipoUsuario(any(UUID.class));
        verify(tipoUsuarioGateway, times(1)).existsUsuarioComEsteTipo(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar um TipoUsuario que não existe")
    void deveLancarExcecaoNaoExisteTipoUsuario() {
        when(tipoUsuarioGateway.existsTipoUsuario(any(UUID.class))).thenReturn(false);

        TipoUsuarioNaoEncontradoException exception = assertThrows(TipoUsuarioNaoEncontradoException.class, () -> {
            deletarTipoUsuarioUseCase.executar(UUID.randomUUID());
        });

        assertEquals("Tipo de usuário não encontrado", exception.getMessage());
        verify(tipoUsuarioGateway, times(1)).existsTipoUsuario(any(UUID.class));
        verify(tipoUsuarioGateway, never()).deletar(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar um TipoUsuario que possui usuários associados")
    void deveLancarExcecaoExisteUsuarioComTipoUsuario() {
        when(tipoUsuarioGateway.existsTipoUsuario(any(UUID.class))).thenReturn(true);
        when(tipoUsuarioGateway.existsUsuarioComEsteTipo(any(UUID.class))).thenReturn(true);

        TipoUsuarioComUsuarioVinculadoException exception = assertThrows(TipoUsuarioComUsuarioVinculadoException.class, () -> {
            deletarTipoUsuarioUseCase.executar(UUID.randomUUID());
        });

        assertEquals("Não é possível excluir o tipo de usuário pois existem usuários associados a ele.", exception.getMessage());
        verify(tipoUsuarioGateway, times(1)).existsTipoUsuario(any(UUID.class));
        verify(tipoUsuarioGateway, times(1)).existsUsuarioComEsteTipo(any(UUID.class));
        verify(tipoUsuarioGateway, never()).deletar(any(UUID.class));
    }

}
