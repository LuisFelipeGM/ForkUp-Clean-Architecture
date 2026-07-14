package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes unitários para a classe EnderecoMapper")
public class EnderecoMapperTest {

    private final EnderecoMapper mapper = new EnderecoMapper();

    @Test
    @DisplayName("Deve retornar null ao converter um endereço nulo para entidade")
    void deveRetornarNullQuandoEnderecoRequestForNulo() {
        assertNull(mapper.toDomain(null));
    }

}
