package com.fiap.forkup.clean.arch.infra.web.vo;

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
    private String nome;

    @NotBlank(message = "O tipo de cozinha não pode ser vazio")
    private String tipoCozinha;

    @NotBlank(message = "O horário de funcionamento não pode ser vazio")
    private String horarioFuncionamento;

    @NotNull(message = "O ID do dono não pode ser nulo")
    private UUID donoId;

    @Valid
    @NotNull(message = "O endereço não pode ser nulo")
    private EnderecoVO endereco;

}
