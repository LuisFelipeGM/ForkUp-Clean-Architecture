package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteCreateVO {

    @NotBlank(message = "O nome do restaurante não pode ser vazio")
    @Schema(description = "Nome do restaurante", example = "Restaurante Saboroso")
    private String nome;

    @NotBlank(message = "O tipo de cozinha não pode ser vazio")
    @Schema(description = "Tipo de cozinha do restaurante", example = "Italiana")
    private String tipoCozinha;

    @NotBlank(message = "O horário de funcionamento não pode ser vazio")
    @Schema(description = "Horário de funcionamento do restaurante", example = "10:00 - 22:00")
    private String horarioFuncionamento;

    @NotNull(message = "O ID do dono não pode ser nulo")
    @Schema(description = "ID do dono do restaurante", example = "62eb72f1-036c-41c6-aec2-f42c658df1e6")
    private UUID donoId;

    @Valid
    @NotNull(message = "O endereço não pode ser nulo")
    @Schema(description = "Endereço do restaurante")
    private EnderecoVO endereco;

}
