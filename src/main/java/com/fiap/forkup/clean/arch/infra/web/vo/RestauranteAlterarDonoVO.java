package com.fiap.forkup.clean.arch.infra.web.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteAlterarDonoVO {

    @NotNull(message = "O ID do novo dono não pode ser nulo")
    private UUID idDono;

}
