package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.infra.persistence.mapper.EnderecoEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes unitários para a classe EnderecoEntityMapper")
public class EnderecoEntityMapperTest {

    private EnderecoEntityMapper mapper = new EnderecoEntityMapper();

    @Test
    @DisplayName("Deve retornar null ao converter um endereço nulo para entidade")
    void deveRetornarNullQuandoEnderecoForNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve retornar null ao converter uma entidade nula para domínio")
    void deveRetornarNullQuandoJpaEntityForNula() {
        assertNull(mapper.toDomain(null));
    }

}
