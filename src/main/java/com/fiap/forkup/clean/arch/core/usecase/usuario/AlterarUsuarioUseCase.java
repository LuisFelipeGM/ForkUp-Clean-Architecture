package com.fiap.forkup.clean.arch.core.usecase.usuario;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.UsuarioRequestUpdate;
import com.fiap.forkup.clean.arch.core.dto.UsuarioReponseFull;
import com.fiap.forkup.clean.arch.core.exception.EmailUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.LoginUsuarioJaCadastradoException;
import com.fiap.forkup.clean.arch.core.exception.UsuarioNaoEncontradoException;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AlterarUsuarioUseCase {

    private UsuarioGateway usuarioGateway;

    private UsuarioMapper usuarioMapper;

    private EnderecoMapper enderecoMapper;

    public UsuarioReponseFull execute(final UUID id, final UsuarioRequestUpdate usuarioRequestUpdate) {
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        validarUpdate(id, usuarioRequestUpdate);

        Endereco endereco = enderecoMapper.toDomain(usuarioRequestUpdate.endereco());

        usuario.atualizarUsuario(usuarioRequestUpdate.nome(), usuarioRequestUpdate.email(), usuarioRequestUpdate.login(), endereco);
        usuarioGateway.atualizar(usuario);

        return usuarioMapper.domainToDtoFull(usuario);
    }

    private void validarUpdate(final UUID id, final UsuarioRequestUpdate usuarioRequestUpdate) {
        if (usuarioGateway.existsUsuarioComEsteLoginAndIdNot(usuarioRequestUpdate.login(), id)) {
            throw new LoginUsuarioJaCadastradoException("Já existe um usuário com este login");
        }

        if (usuarioGateway.exitsUsuarioComEsteEmailAndIdNot(usuarioRequestUpdate.email(), id)) {
            throw new EmailUsuarioJaCadastradoException("Já existe um usuário com este email");
        }
    }

}
