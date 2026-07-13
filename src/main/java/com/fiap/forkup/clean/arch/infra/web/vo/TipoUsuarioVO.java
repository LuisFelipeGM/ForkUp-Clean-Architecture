package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Descrição do tipo de usuário", example = "Administrador")
    private String descricao;

}
