package com.fiap.forkup.clean.arch.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.AlterarDescricaoTipoUsuarioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso AlterarDescricaoTipoUsuarioUseCase")
public class AlterarDescricaoTipoUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AlterarDescricaoTipoUsuarioUseCase alterarDescricaoTipoUsuarioUseCase;

    @Test
    @DisplayName("Deve alterar a descrição de um TipoUsuario com sucesso")
    void deveAlterarDescricaoTipoUsuarioComSucesso() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");
        String novaDescricao = "Dono do Restaurante";

        when(tipoUsuarioGateway.buscarPorId(tipoUsuario.getId())).thenReturn(Optional.of(tipoUsuario));

        TipoUsuario tipoUsuarioAtualizado = alterarDescricaoTipoUsuarioUseCase.executar(tipoUsuario.getId(), novaDescricao);

        assertEquals(novaDescricao, tipoUsuarioAtualizado.getDescricao());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuario.getId());
        verify(tipoUsuarioGateway, times(1)).atualizar(tipoUsuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar alterar a descrição de um TipoUsuario que não existe")
    void deveLancarExcecaoTipoDescricaoNaoEncontrada() {

        when(tipoUsuarioGateway.buscarPorId(any())).thenReturn(Optional.empty());

        TipoUsuarioNaoEncontradoException exception = assertThrows(TipoUsuarioNaoEncontradoException.class, () ->
                alterarDescricaoTipoUsuarioUseCase.executar(UUID.randomUUID(), "Nova Descrição"));

        assertEquals("Tipo Usuário não encontrado", exception.getMessage());
        verify(tipoUsuarioGateway, times(1)).buscarPorId(any());
        verify(tipoUsuarioGateway, never()).atualizar(any());

    }

}
