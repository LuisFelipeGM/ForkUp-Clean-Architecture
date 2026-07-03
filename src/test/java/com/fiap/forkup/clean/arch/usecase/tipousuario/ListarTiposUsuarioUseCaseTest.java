package com.fiap.forkup.clean.arch.usecase.tipousuario;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.ListarTiposUsuarioUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso ListarTiposUsuarioUseCase")
public class ListarTiposUsuarioUseCaseTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    @Test
    @DisplayName("Deve listar todos os TiposUsuario com sucesso")
    void deveListarTodosOsTiposUsuarioComSucesso() {
        TipoUsuario tipoUsuario1 = new TipoUsuario("Cliente");
        TipoUsuario tipoUsuario2 = new TipoUsuario("Dono do Restaurante");

        List<TipoUsuario> tiposUsuario = List.of(tipoUsuario1, tipoUsuario2);

        when(tipoUsuarioGateway.listarTodos()).thenReturn(tiposUsuario);

        List<TipoUsuario> resultado = listarTiposUsuarioUseCase.executar();

        assertEquals(tiposUsuario.size(), resultado.size());
        assertEquals(tiposUsuario.getFirst().getDescricao(), resultado.getFirst().getDescricao());
        assertEquals(tiposUsuario.getLast().getDescricao(), resultado.getLast().getDescricao());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver TiposUsuario cadastrados")
    void deveRetornarListaVaziaSemTiposUsuario() {
        List<TipoUsuario> tiposUsuario = List.of();

        when(tipoUsuarioGateway.listarTodos()).thenReturn(tiposUsuario);

        List<TipoUsuario> resultado = listarTiposUsuarioUseCase.executar();

        assertEquals(0, resultado.size());
    }

}
