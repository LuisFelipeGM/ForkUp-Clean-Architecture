package com.fiap.forkup.clean.arch.domain;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.exception.EnderecoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe Endereco")
public class EnderecoTest {

    @Test
    @DisplayName("Deve criar um Endereco válido")
    void deveCriarEnderecoValido() {
        UUID id = UUID.randomUUID();
        String logradouro = "Rua A";
        String numero = "123";
        String complemento = "Apto 101";
        String cidade = "São Paulo";
        String cep = "12345-678";

        Endereco endereco = new Endereco(id, logradouro, numero, complemento, cidade, cep);

        assertNotNull(endereco);
        assertNotNull(endereco.getId());
        assertEquals(id, endereco.getId());
        assertEquals(logradouro, endereco.getLogradouro());
        assertEquals(numero, endereco.getNumero());
        assertEquals(complemento, endereco.getComplemento());
        assertEquals(cidade, endereco.getCidade());

    }

    @Test
    @DisplayName("Deve criar um Endereco válido")
    void deveCriarEnderecoSemComplementoComSucesso() {
        UUID id = UUID.randomUUID();
        String logradouro = "Rua A";
        String numero = "123";
        String cidade = "São Paulo";
        String cep = "12345-678";

        Endereco endereco = new Endereco(id, logradouro, numero, null, cidade, cep);

        assertNull(endereco.getComplemento());

    }

    @Test
    @DisplayName("Deve normalizar o CEP removendo caracteres não numéricos")
    void deveNormalizarCep() {
        String cep = "12345-678";
        Endereco endereco = new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", "São Paulo", cep);
        assertEquals("12345678", endereco.getCep());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o logradouro for nulo")
    void deveLancarExcecaoLogradouroForNulo() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), null, "123", "Apto 101", "São Paulo", "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o logradouro for vazio")
    void deveLancarExcecaoLogradouroForVazio() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "", "123", "Apto 101", "São Paulo", "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o número for nulo")
    void deveLancarExcecaoNumeroForNulo() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", null, "Apto 101", "São Paulo", "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o número for vazio")
    void deveLancarExcecaoNumeroForVazio() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", "", "Apto 101", "São Paulo", "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cidade for nulo")
    void deveLancarExcecaoCidadeForNulo() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", null, "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cidade for vazio")
    void deveLancarExcecaoCidadeForVazio() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", "", "12345-678");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cep for nulo")
    void deveLancarExcecaoCepForNulo() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", "São Paulo", null);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cep for inválido")
    void deveLancarExcecaoCepForInvalido() {
        assertThrows(EnderecoInvalidoException.class, () -> {
            new Endereco(UUID.randomUUID(), "Rua A", "123", "Apto 101", "São Paulo", "123456789");
        });
    }

}
