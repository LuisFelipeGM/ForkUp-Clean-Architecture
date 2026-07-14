package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.*;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.ItemCardapioEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@DisplayName("Testes unitários para a classe RestauranteEntityMapper")
public class RestauranteEntityMapperTest {

    private RestauranteEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestauranteEntityMapper(
                new EnderecoEntityMapper(),
                new ItemCardapioEntityMapper()
        );
    }

    @Test
    @DisplayName("Deve retornar null ao converter um domínio nulo para entidade")
    void deveRetornarNullQuandoRestauranteForNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve retornar null ao converter uma entidade nula para domínio")
    void deveRetornarNullQuandoJpaEntityForNula() {
        assertNull(mapper.toDomain(null));
    }

}
