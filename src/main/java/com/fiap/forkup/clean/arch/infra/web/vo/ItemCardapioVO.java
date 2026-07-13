package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Nome do item do cardápio", example = "Pizza Margherita")
    private String nome;

    @NotBlank(message = "A descrição do item não pode ser vazia")
    @Schema(description = "Descrição do item do cardápio", example = "Pizza tradicional com mussarela e tomate")
    private String descricao;

    @NotNull(message = "O preço do item não pode ser nulo")
    @Schema(description = "Preço do item do cardápio", example = "29.90")
    private BigDecimal preco;

    @NotNull(message = "O campo apenasRestaurante não pode ser nulo")
    @Schema(description = "Indica se o item é apenas para o restaurante", example = "true")
    private Boolean apenasRestaurante;

    @NotBlank(message = "O caminho da foto não pode ser vazio")
    @Schema(description = "Caminho da foto do item do cardápio", example = "/fotos/pizza_margherita.jpg")
    private String pathFoto;

}
