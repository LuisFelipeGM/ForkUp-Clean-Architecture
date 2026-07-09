package com.fiap.forkup.clean.arch.infra.web.vo;

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
    private UUID tipoUsuario;

}
