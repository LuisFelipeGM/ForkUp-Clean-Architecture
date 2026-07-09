package com.fiap.forkup.clean.arch.infra.web.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoVO {

    @NotNull(message = "O logradouro não pode ser nulo")
    private String logradouro;

    @NotNull(message = "O número não pode ser nulo")
    private String numero;

    private String complemento;

    @NotNull(message = "A cidade não pode ser nula")
    private String cidade;

    @NotNull(message = "O CEP não pode ser nulo")
    private String cep;

}
