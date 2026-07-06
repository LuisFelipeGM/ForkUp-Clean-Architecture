package com.fiap.forkup.clean.arch.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.CriarTipoUsuarioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso CriarTipoUsuarioUseCase")
public class CriarTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @Test
    @DisplayName("Deve criar um TipoUsuario com sucesso")
    void deveCriarTipoUsuarioComSucesso() {
        TipoUsuario tipoUsuario = new TipoUsuario("Cliente");

        when(tipoUsuarioGateway.existsByDescricao(tipoUsuario.getDescricao())).thenReturn(false);
        when(tipoUsuarioGateway.criar(any())).thenReturn(tipoUsuario.getId());

        UUID idCriado = criarTipoUsuarioUseCase.executar(tipoUsuario);

        assertNotNull(idCriado);
        verify(tipoUsuarioGateway, times(1)).criar(any());
        verify(tipoUsuarioGateway, times(1)).existsByDescricao(tipoUsuario.getDescricao());

    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar criar um TipoUsuario com descrição já existente")
    void deveLancarExecaoDescricaoTipoUsuarioJaExiste() {

        TipoUsuario tipoUsuario = new TipoUsuario("Cliente");

        when(tipoUsuarioGateway.existsByDescricao(tipoUsuario.getDescricao())).thenReturn(true);

        TipoUsuarioJaCadastradoException exception = assertThrows(TipoUsuarioJaCadastradoException.class, () ->
                criarTipoUsuarioUseCase.executar(tipoUsuario));
        assertEquals("Tipo Usuário já cadastrado: " + tipoUsuario.getDescricao(), exception.getMessage());
        verify(tipoUsuarioGateway, times(1)).existsByDescricao(tipoUsuario.getDescricao());
        verify(tipoUsuarioGateway, never()).criar(any());
    }

}