package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoVO {

    @NotNull(message = "O logradouro não pode ser nulo")
    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @NotNull(message = "O número não pode ser nulo")
    @Schema(description = "Número do endereço", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @NotNull(message = "A cidade não pode ser nula")
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NotNull(message = "O CEP não pode ser nulo")
    @Schema(description = "CEP do endereço", example = "12345-678")
    private String cep;

}
