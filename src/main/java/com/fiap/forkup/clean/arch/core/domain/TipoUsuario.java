package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioInvalidoException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TipoUsuario {

    private UUID id;
    private String descricao;
    private LocalDateTime criadoEm;

    public TipoUsuario(UUID id, String descricao) {
        validarDescricao(descricao);
        this.id = id;
        this.descricao = descricao;
        this.criadoEm = LocalDateTime.now();
    }

    public TipoUsuario(String descricao) {
        this(UUID.randomUUID(), descricao);
    }

    public void alterarDescricao(String novaDescricao) {
        validarDescricao(novaDescricao);
        this.descricao = novaDescricao.trim();
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank())
            throw new TipoUsuarioInvalidoException("Descrição do tipo de usuário não pode ser nula ou vazia.");

        if (descricao.trim().length() > 50)
            throw new TipoUsuarioInvalidoException("Descrição do tipo deve ter no máximo 50 caracteres.");
    }

}