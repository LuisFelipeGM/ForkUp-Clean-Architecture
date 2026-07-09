package com.fiap.forkup.clean.arch.infra.web.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCardapioVO {

    @NotBlank(message = "O nome do item não pode ser vazio")
    private String nome;

    @NotBlank(message = "A descrição do item não pode ser vazia")
    private String descricao;

    @NotNull(message = "O preço do item não pode ser nulo")
    private BigDecimal preco;

    @NotNull(message = "O campo apenasRestaurante não pode ser nulo")
    private Boolean apenasRestaurante;

    @NotBlank(message = "O caminho da foto não pode ser vazio")
    private String pathFoto;

}
