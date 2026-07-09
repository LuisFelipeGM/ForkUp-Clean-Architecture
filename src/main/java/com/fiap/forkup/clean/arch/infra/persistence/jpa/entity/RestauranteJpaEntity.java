package com.fiap.forkup.clean.arch.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "restaurante")
public class RestauranteJpaEntity {

    @Id
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_cozinha")
    private String tipoCozinha;

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private EnderecoJpaEntity enderecoJpaEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dono_id")
    private UsuarioJpaEntity dono;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCardapioJpaEntity> cardapio;

}
