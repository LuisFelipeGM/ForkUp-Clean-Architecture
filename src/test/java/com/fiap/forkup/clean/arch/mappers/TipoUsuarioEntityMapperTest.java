package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.infra.persistence.mapper.TipoUsuarioEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes unitários para a classe TipoUsuarioEntityMapper")
public class TipoUsuarioEntityMapperTest {

    private TipoUsuarioEntityMapper mapper = new TipoUsuarioEntityMapper();

    @Test
    @DisplayName("Deve retornar null ao converter um domínio nulo para entidade")
    void deveRetornarNullQuandoTipoUsuarioForNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve retornar null ao converter uma entidade nula para domínio")
    void deveRetornarNullQuandoJpaEntityForNula() {
        assertNull(mapper.toDomain(null));
    }

}
