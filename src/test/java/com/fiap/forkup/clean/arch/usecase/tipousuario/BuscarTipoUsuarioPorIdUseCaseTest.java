package com.fiap.forkup.clean.arch.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.BuscarTipoUsuarioPorIdUseCase;
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
@DisplayName("Testes unitários para o caso de uso BuscarTipoUsuarioPorIdUseCase")
public class BuscarTipoUsuarioPorIdUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Test
    @DisplayName("Deve retornar um TipoUsuario por ID com sucesso")
    void deveRetornarTipoUsuarioPorIdComSucesso() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoUsuario = new TipoUsuario(id, "Cliente");

        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.of(tipoUsuario));

        TipoUsuarioReponse retorno = buscarTipoUsuarioPorIdUseCase.executar(id);

        assertNotNull(retorno);
        assertEquals(tipoUsuario.getId(), retorno.id());
        assertEquals(tipoUsuario.getDescricao(), retorno.descricao());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar um TipoUsuario por ID")
    void deveLancarExcecaoTipoUsuarioNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        TipoUsuarioNaoEncontradoException exception = assertThrows(TipoUsuarioNaoEncontradoException.class, () -> buscarTipoUsuarioPorIdUseCase.executar(id));

        assertEquals("Tipo Usuário não encontrado", exception.getMessage());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(id);
    }

}
