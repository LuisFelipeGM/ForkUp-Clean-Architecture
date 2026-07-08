package com.fiap.forkup.clean.arch.core.gateway;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.Pagina;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteGateway {

    Pagina<Restaurante> listarTodos(Integer pagina, Integer tamanho);

    Optional<Restaurante> buscarPorId(UUID id);

    Optional<Restaurante> buscarPorIdComCardapio(UUID id);

    UUID criar(Restaurante restaurante);

    boolean existsRestaurante(UUID id);

    void deletar(UUID id);

    void atualizar(Restaurante restaurante);

    String nomeDonoVinculadoRestaurante(UUID idRestaurante);

    List<ItemCardapio> listarCardapio(UUID idRestaurante);

}
