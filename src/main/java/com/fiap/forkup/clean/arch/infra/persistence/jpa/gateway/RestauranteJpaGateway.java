package com.fiap.forkup.clean.arch.infra.persistence.jpa.gateway;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.ItemCardapioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.ItemCardapioRepository;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.RestauranteRepository;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.ItemCardapioEntityMapper;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.PaginaMapper;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.RestauranteEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

    private final RestauranteRepository restauranteRepository;

    private final ItemCardapioRepository itemCardapioRepository;

    private final RestauranteEntityMapper restauranteEntityMapper;

    private final ItemCardapioEntityMapper itemCardapioEntityMapper;

    @Override
    public Pagina<Restaurante> listarTodos(Integer pagina, Integer tamanho) {

        Page<RestauranteJpaEntity> page =
                restauranteRepository.findAll(PageRequest.of(pagina, tamanho));

        return PaginaMapper
                .from(page)
                .map(restauranteEntityMapper::toDomain);
    }

    @Override
    public Optional<Restaurante> buscarPorId(UUID id) {

        Optional<RestauranteJpaEntity> entity = restauranteRepository.findById(id);

        if (entity.isEmpty()) {
            log.warn("Restaurante com id {} não encontrado", id);
            return Optional.empty();
        }

        return Optional.of(restauranteEntityMapper.toDomain(entity.get()));
    }

    @Override
    public Optional<Restaurante> buscarPorIdComCardapio(UUID id) {
        Optional<RestauranteJpaEntity> entity = restauranteRepository.buscarPorIdComCardapio(id);

        if (entity.isEmpty()) {
            log.warn("Restaurante completo com id {} não encontrado", id);
            return Optional.empty();
        }

        return Optional.of(restauranteEntityMapper.toDomain(entity.get()));
    }

    @Override
    public UUID criar(Restaurante restaurante) {
        RestauranteJpaEntity restauranteJpa = restauranteEntityMapper.toEntity(restaurante);
        restauranteJpa = restauranteRepository.save(restauranteJpa);

        log.info("Restaurante {} criado com sucesso", restauranteJpa.getNome());

        return restauranteJpa.getId();
    }

    @Override
    public boolean existsRestaurante(UUID id) {
        return restauranteRepository.existsById(id);
    }

    @Override
    public void deletar(UUID id) {
        restauranteRepository.deleteById(id);
        log.info("Restaurante com id {} deletado com sucesso", id);
    }

    @Override
    public void atualizar(Restaurante restaurante) {
        RestauranteJpaEntity restauranteJpa = restauranteEntityMapper.toEntity(restaurante);
        restauranteRepository.save(restauranteJpa);
        log.info("Restaurante {} atualizado com sucesso", restauranteJpa.getNome());
    }

    @Override
    public String nomeDonoVinculadoRestaurante(UUID idRestaurante) {
        return restauranteRepository.nomeDono(idRestaurante);
    }

    @Override
    public List<ItemCardapio> listarCardapio(UUID idRestaurante) {
        List<ItemCardapioJpaEntity> cardapio = itemCardapioRepository.listarCardapio(idRestaurante);
        return itemCardapioEntityMapper.toDomainList(cardapio);
    }

}
