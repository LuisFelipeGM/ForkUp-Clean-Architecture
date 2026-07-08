package com.fiap.forkup.clean.arch.infra.persistence.jpa.repository;

import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsuarioJpaEntity u " +
            "WHERE u.id = :idUsuario " +
            "AND u.tipoUsuario.id = :idTipoUsuarioDono")
    boolean existsUsuarioDono(UUID idUsuario, UUID idTipoUsuarioDono);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM RestauranteJpaEntity r " +
            "JOIN r.dono u " +
            "WHERE u.id = :usuarioId")
    boolean existsRestauranteVinculadoUsuario(UUID usuarioId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsuarioJpaEntity u " +
            "WHERE lower(u.login) = lower(:login) " +
            "AND u.id <> :idUsuario")
    boolean existsUsuarioComLoginAndIdNot(String login, UUID idUsuario);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsuarioJpaEntity u " +
            "WHERE lower(u.login) = lower(:login)")
    boolean existsUsuarioComLogin(String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsuarioJpaEntity u " +
            "WHERE lower(u.email) = lower(:email) " +
            "AND u.id <> :idUsuario")
    boolean existsUsuarioComEmailAndIdNot(String email, UUID idUsuario);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UsuarioJpaEntity u " +
            "WHERE lower(u.email) = lower(:email)")
    boolean existsUsuarioComEmail(String email);

}
