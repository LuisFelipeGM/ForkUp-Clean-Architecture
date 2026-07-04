package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioInvalidoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TipoUsuario {

    private UUID id;
    private String descricao;
    private LocalDateTime criadoEm;

}
