package com.fiap.forkup.clean.arch.core.gateway;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.PaginacaoRequest;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioGateway {

    Pagina<Usuario> listarTodos(PaginacaoRequest paginacao);

    Optional<Usuario> buscarPorId(UUID id);

    UUID criar(Usuario usuario);

    void atualizar(Usuario usuario);

    boolean existsUsuario(UUID id);

    boolean existsUsuarioDono(UUID id);

    boolean existsRestauranteVinculadoUsuario(UUID id);

    boolean existsUsuarioComEsteLoginAndIdNot(String login, UUID id);

    boolean existsUsuarioComEsteLogin(String login);

    boolean exitsUsuarioComEsteEmailAndIdNot(String email, UUID id);

    boolean exitsUsuarioComEsteEmail(String email);

    boolean usuarioVinculadoRestaurante(UUID id);

    void deletar(UUID id);
}
