package com.fiap.forkup.clean.arch.infra.web.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioVO {

    @NotBlank(message = "A descrição do tipo de usuário não pode ser nula")
    private String descricao;

}
