package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAlterarTipoVO {

    @NotNull(message = "Tipo de usuário é obrigatório")
    @Schema(description = "ID do tipo de usuário", example = "b84d7424-bfa4-43bb-b423-98955da03499")
    private UUID tipoUsuario;

}
