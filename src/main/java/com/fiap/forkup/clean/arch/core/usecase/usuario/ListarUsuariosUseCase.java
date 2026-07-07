package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ListarUsuariosUseCase {

    private final UsuarioGateway usuarioGateway;

    public Pagina<Usuario> execute(int pagina, int tamanho) {
        return usuarioGateway.listarTodos(pagina, tamanho);
    }

}
