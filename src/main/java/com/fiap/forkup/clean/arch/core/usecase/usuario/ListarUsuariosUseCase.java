package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.PaginacaoRequest;
import com.fiap.forkup.clean.arch.core.dto.UsuarioResponsePartial;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ListarUsuariosUseCase {

    private final UsuarioGateway usuarioGateway;

    private final UsuarioMapper usuarioMapper;

    public Pagina<UsuarioResponsePartial> execute(PaginacaoRequest paginacaoRequest) {
        return usuarioGateway
                .listarTodos(paginacaoRequest)
                .map(usuarioMapper::domainToDtoPartial);
    }

}
