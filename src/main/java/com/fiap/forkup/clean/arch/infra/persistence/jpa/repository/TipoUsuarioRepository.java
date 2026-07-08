package com.fiap.forkup.clean.arch.infra.persistence.jpa.repository;

import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.TipoUsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM TipoUsuarioJpaEntity t " +
            "WHERE lower(t.descricao) = lower(:descricao)")
    boolean existsByDescricao(String descricao);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM TipoUsuarioJpaEntity t " +
            "LEFT JOIN  t.usuarios u " +
            "WHERE t.id = :id")
    boolean existUsuarioVinculadoTipoUsuario(UUID id);

}
