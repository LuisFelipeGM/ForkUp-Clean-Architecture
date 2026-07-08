package com.fiap.forkup.clean.arch.infra.persistence.jpa.repository;


import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.ItemCardapioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteJpaEntity, UUID> {

    @Query(" SELECT r " +
            "FROM RestauranteJpaEntity r " +
            "LEFT JOIN FETCH r.cardapio " +
            "WHERE r.id = :id")
    Optional<RestauranteJpaEntity> buscarPorIdComCardapio(UUID id);

    @Query("SELECT u.nome " +
            "FROM RestauranteJpaEntity r " +
            "LEFT JOIN r.dono u " +
            "WHERE r.id = :id")
    String nomeDono(UUID id);

}
