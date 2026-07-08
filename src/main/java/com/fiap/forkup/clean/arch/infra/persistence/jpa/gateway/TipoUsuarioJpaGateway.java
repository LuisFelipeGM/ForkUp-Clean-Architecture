package com.fiap.forkup.clean.arch.infra.persistence.jpa.gateway;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.TipoUsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.TipoUsuarioRepository;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.TipoUsuarioEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class TipoUsuarioJpaGateway implements TipoUsuarioGateway {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    private final TipoUsuarioEntityMapper tipoUsuarioEntityMapper;

    @Override
    public Optional<TipoUsuario> buscarPorId(UUID id) {

        Optional<TipoUsuarioJpaEntity> entity = tipoUsuarioRepository.findById(id);

        if (entity.isEmpty()) {
            log.warn("TipoUsuario com id {} não encontrado", id);
            return Optional.empty();
        }

        return Optional.of(tipoUsuarioEntityMapper.toDomain(entity.get()));
    }

    @Override
    public List<TipoUsuario> listarTodos() {

        List<TipoUsuarioJpaEntity> entities = tipoUsuarioRepository.findAll();

        log.info("Listando todos os TipoUsuario, total de registros: {}", entities.size());

        return entities.stream()
                .map(tipoUsuarioEntityMapper::toDomain)
                .toList();
    }

    @Override
    public UUID criar(TipoUsuario tipoUsuario) {
        TipoUsuarioJpaEntity tipoUsuarioJpa = tipoUsuarioEntityMapper.toEntity(tipoUsuario);
        tipoUsuarioJpa = tipoUsuarioRepository.save(tipoUsuarioJpa);

        log.info("TipoUsuario {} criado com sucesso", tipoUsuarioJpa.getDescricao());

        return tipoUsuarioJpa.getId();
    }

    @Override
    public void atualizar(TipoUsuario tipoUsuario) {
        TipoUsuarioJpaEntity tipoUsuarioJpa = tipoUsuarioEntityMapper.toEntity(tipoUsuario);
        tipoUsuarioRepository.save(tipoUsuarioJpa);
        log.info("TipoUsuario {} atualizado com sucesso", tipoUsuarioJpa.getDescricao());
    }

    @Override
    public boolean existsTipoUsuario(UUID id) {
        return tipoUsuarioRepository.existsById(id);
    }

    @Override
    public boolean existsByDescricao(String descricao) {
        return tipoUsuarioRepository.existsByDescricao(descricao);
    }

    @Override
    public boolean existsUsuarioComEsteTipo(UUID id) {
        return tipoUsuarioRepository.existUsuarioVinculadoTipoUsuario(id);
    }

    @Override
    public void deletar(UUID id) {
        tipoUsuarioRepository.deleteById(id);
        log.info("TipoUsuario com id {} deletado com sucesso", id);
    }

}
