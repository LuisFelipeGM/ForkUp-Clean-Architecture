package com.fiap.forkup.clean.arch.core.controller;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import com.fiap.forkup.clean.arch.core.usecase.usuario.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UsuarioController {

    private final ListarUsuariosUseCase listarUsuariosUseCase;

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final CriarUsuarioUseCase criarUsuarioUseCase;

    private final AlterarUsuarioUseCase alterarUsuarioUseCase;

    private final AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase;

    private final DeletarUsuarioUseCase deletarUsuarioUseCase;

    private final UsuarioMapper usuarioMapper;

    private final EnderecoMapper enderecoMapper;

    public Pagina<UsuarioResponsePartial> listarTodosPaginado(PaginacaoRequest paginacaoRequest) {
        Pagina<Usuario> usuarioPagina = listarUsuariosUseCase.execute(paginacaoRequest.pagina(), paginacaoRequest.tamanho());
        return usuarioPagina.map(usuarioMapper::domainToDtoPartial);
    }

    public UsuarioReponseFull buscarPorId(final UUID id) {
        Usuario usuario = buscarUsuarioPorIdUseCase.execute(id);
        return usuarioMapper.domainToDtoFull(usuario);
    }

    public UUID criar(final UsuarioRequestCreate usuarioRequestCreate) {
        Usuario usuario = usuarioMapper.requestToDomain(usuarioRequestCreate);
        return criarUsuarioUseCase.execute(usuario);
    }

    public UsuarioReponseFull alterar(final UUID id,final UsuarioRequestUpdate usuarioRequestUpdate) {
        Endereco endereco = enderecoMapper.toDomain(usuarioRequestUpdate.endereco());
        AtualizarUsuarioInput input = new AtualizarUsuarioInput(
                usuarioRequestUpdate.nome(),
                usuarioRequestUpdate.email(),
                usuarioRequestUpdate.login(),
                endereco
        );
        Usuario usuarioAtualizado = alterarUsuarioUseCase.execute(id, input);
        return usuarioMapper.domainToDtoFull(usuarioAtualizado);
    }

    public UsuarioReponseFull alterarTipoUsuario(UUID idUsuario, AtualizarTipoDoUsuarioRequest request) {
        Usuario usuario = alterarTipoDoUsuarioUseCase.execute(idUsuario, request.idTipoUsuario());
        return usuarioMapper.domainToDtoFull(usuario);
    }

    public void deletar(UUID id) {
        deletarUsuarioUseCase.execute(id);
    }

}
