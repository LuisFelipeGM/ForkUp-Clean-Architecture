package com.fiap.forkup.clean.arch.infra.persistence.jpa.repository;

import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.ItemCardapioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapioJpaEntity, UUID> {

    @Query("SELECT i " +
            "FROM ItemCardapioJpaEntity i " +
            "WHERE i.restaurante.id = :restauranteId")
    List<ItemCardapioJpaEntity> listarCardapio(UUID restauranteId);

}
