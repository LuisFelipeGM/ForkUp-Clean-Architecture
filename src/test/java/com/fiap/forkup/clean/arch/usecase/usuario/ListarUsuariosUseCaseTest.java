package com.fiap.forkup.clean.arch.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.usuario.ListarUsuariosUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para o caso de uso ListarUsuariosUseCase")
public class ListarUsuariosUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ListarUsuariosUseCase listarUsuariosUseCase;

    @Test
    @DisplayName("Deve listar usuários com sucesso")
    void deveListarUsuariosComSucesso() {
        Pagina<Usuario> paginaEsperada = new Pagina<>(java.util.List.of(), 0, 10, 1L, 1);

        when(usuarioGateway.listarTodos(0, 10)).thenReturn(paginaEsperada);

        Pagina<Usuario> paginaRetornada = listarUsuariosUseCase.execute(0, 10);

        assertEquals(paginaEsperada, paginaRetornada);
        verify(usuarioGateway, times(1)).listarTodos(0, 10);
    }

}
