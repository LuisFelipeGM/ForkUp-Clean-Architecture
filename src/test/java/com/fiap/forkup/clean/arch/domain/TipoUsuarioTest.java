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

        TipoUsuario tipoUsuario = new TipoUsuario(id, descricao);

        assertNotNull(tipoUsuario);
        assertNotNull(tipoUsuario.getId());
        assertEquals(id, tipoUsuario.getId());
        assertEquals(descricao, tipoUsuario.getDescricao());
        assertNotNull(tipoUsuario.getCriadoEm());
    }

    @Test
    @DisplayName("Deve alterar a descrição de um TipoUsuario com sucesso")
    void deveAlterarComSucessoDescricaoTipoUsuario() {
        UUID id = UUID.randomUUID();
        String descricao = "Cliente";

        TipoUsuario tipoUsuario = new TipoUsuario(id, descricao);

        String novaDescricao = "Consumidor";

        tipoUsuario.alterarDescricao(novaDescricao);

        assertEquals(novaDescricao, tipoUsuario.getDescricao());
        assertNotEquals(descricao, tipoUsuario.getDescricao());
    }

    @Test
    @DisplayName("Deve alterar a descrição de um TipoUsuario removendo espaços nas bordas")
    void deveAlterarComSucessoDescricaoRemovendoEspacosNasBordas() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");

        tipoUsuario.alterarDescricao(" Consumidor ");

        assertEquals("Consumidor", tipoUsuario.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for nula")
    void deveLancarExcecaoQuandoDescricaoForNula() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");

        assertThrows(TipoUsuarioInvalidoException.class, () -> {
            tipoUsuario.alterarDescricao(null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição for vazia")
    void deveLancarExcecaoQuandoDescricaoForVazia() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");

        assertThrows(TipoUsuarioInvalidoException.class, () -> {
            tipoUsuario.alterarDescricao("");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a descrição passar de cinquenta caracteres")
    void deveLancarExcecaoQuandoDescricaoPassarDeCinquentaCaracteres() {
        TipoUsuario tipoUsuario = new TipoUsuario(UUID.randomUUID(), "Cliente");

        assertThrows(TipoUsuarioInvalidoException.class, () -> {
            tipoUsuario.alterarDescricao("Descrição muito longa que ultrapassa cinquenta caracteres");
        });
    }

}
