package com.fiap.forkup.clean.arch.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioJpaEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "tipoUsuario")
    private List<UsuarioJpaEntity> usuarios;

}
