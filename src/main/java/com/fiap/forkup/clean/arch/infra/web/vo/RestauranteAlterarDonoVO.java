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
public class RestauranteAlterarDonoVO {

    @NotNull(message = "O ID do novo dono não pode ser nulo")
    @Schema(description = "ID do novo dono do restaurante", example = "f718f2cb-b98d-4c2e-8d03-8b293cd91490")
    private UUID idDono;

}
