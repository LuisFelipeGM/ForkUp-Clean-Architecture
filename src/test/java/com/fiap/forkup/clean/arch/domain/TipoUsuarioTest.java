package com.fiap.forkup.clean.arch.domain;

import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.exception.TipoUsuarioInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe TipoUsuario")
public class TipoUsuarioTest {

    @Test
    @DisplayName("Deve criar um TipoUsuario válido")
    void deveCriarTipoUsuarioValido() {
        UUID id = UUID.randomUUID();
        String descricao = "Cliente";

        TipoUsuario tipoUsuario = new TipoUsuario(id, descricao, LocalDateTime.now());

        assertNotNull(tipoUsuario);
        assertNotNull(tipoUsuario.getId());
        assertEquals(id, tipoUsuario.getId());
        assertEquals(descricao, tipoUsuario.getDescricao());
        assertNotNull(tipoUsuario.getCriadoEm());
    }

}
