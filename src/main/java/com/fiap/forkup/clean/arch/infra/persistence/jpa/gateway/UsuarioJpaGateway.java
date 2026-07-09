package com.fiap.forkup.clean.arch.infra.persistence.jpa.gateway;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.EnderecoRepository;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.UsuarioRepository;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.PaginaMapper;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.UsuarioEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class UsuarioJpaGateway implements UsuarioGateway {

    public static final UUID ID_DONO = UUID.fromString("2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b");
    public static final UUID ID_CLIENTE = UUID.fromString("1e7b8f9e-1c2d-4a5b-8f9e-1c2d4a5b8f9e");

    private final UsuarioRepository usuarioRepository;

    private final EnderecoRepository enderecoRepository;

    private final UsuarioEntityMapper usuarioEntityMapper;


    @Override
    public Pagina<Usuario> listarTodos(Integer pagina, Integer tamanho) {

        Page<UsuarioJpaEntity> page =
                usuarioRepository.findAll(PageRequest.of(pagina, tamanho));

        return PaginaMapper
                .from(page)
                .map(usuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {

        Optional<UsuarioJpaEntity> entity = usuarioRepository.findById(id);

        if (entity.isEmpty()) {
            log.warn("Usuario com id {} não encontrado", id);
            return Optional.empty();
        }

        return Optional.of(usuarioEntityMapper.toDomain(entity.get()));
    }

    @Override
    public UUID criar(Usuario usuario) {
        UsuarioJpaEntity usuarioJpa = usuarioEntityMapper.toEntity(usuario);
        EnderecoJpaEntity endereco = enderecoRepository.save(usuarioJpa.getEndereco());
        usuarioJpa.setEndereco(endereco);
        usuarioJpa = usuarioRepository.save(usuarioJpa);

        log.info("Usuario {} criado com sucesso", usuarioJpa.getLogin());

        return usuarioJpa.getId();
    }

    @Override
    public void atualizar(Usuario usuario) {
        UsuarioJpaEntity usuarioJpa = usuarioEntityMapper.toEntity(usuario);
        EnderecoJpaEntity endereco = enderecoRepository.save(usuarioJpa.getEndereco());
        usuarioJpa.setEndereco(endereco);
        usuarioRepository.save(usuarioJpa);
        log.info("Usuario {} atualizado com sucesso", usuarioJpa.getLogin());
    }

    @Override
    public boolean existsUsuario(UUID id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean existsUsuarioDono(UUID id) {
        return usuarioRepository.existsUsuarioDono(id, ID_DONO);
    }

    @Override
    public boolean existsRestauranteVinculadoUsuario(UUID id) {
        return usuarioRepository.existsRestauranteVinculadoUsuario(id);
    }

    @Override
    public boolean existsUsuarioComEsteLoginAndIdNot(String login, UUID id) {
        return usuarioRepository.existsUsuarioComLoginAndIdNot(login, id);
    }

    @Override
    public boolean existsUsuarioComEsteLogin(String login) {
        return usuarioRepository.existsUsuarioComLogin(login);
    }

    @Override
    public boolean existsUsuarioComEsteEmailAndIdNot(String email, UUID id) {
        return usuarioRepository.existsUsuarioComEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsUsuarioComEsteEmail(String email) {
        return usuarioRepository.existsUsuarioComEmail(email);
    }

    @Override
    public void deletar(UUID id) {
        usuarioRepository.deleteById(id);
        log.info("Usuario com id {} deletado com sucesso", id);
    }
}
