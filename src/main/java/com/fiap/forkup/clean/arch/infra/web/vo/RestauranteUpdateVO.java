package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteUpdateVO {

    @NotBlank(message = "O nome do restaurante não pode ser vazio")
    @Schema(description = "Nome do restaurante", example = "Restaurante Saboroso")
    private String nome;

    @NotBlank(message = "O tipo de cozinha não pode ser vazio")
    @Schema(description = "Tipo de cozinha do restaurante", example = "Italiana")
    private String tipoCozinha;

    @NotBlank(message = "O horário de funcionamento não pode ser vazio")
    @Schema(description = "Horário de funcionamento do restaurante", example = "10:00 - 22:00")
    private String horarioFuncionamento;

    @Valid
    @NotNull(message = "O endereço não pode ser nulo")
    @Schema(description = "Endereço do restaurante")
    private EnderecoVO endereco;

}
