package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.ItemCardapioEntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe ItemCardapioEntityMapper")
public class ItemCardapioEntityMapperTest {

    private ItemCardapioEntityMapper mapper = new ItemCardapioEntityMapper();

    @Test
    @DisplayName("Deve retornar null ao converter um domínio nulo para entidade")
    void deveRetornarNullQuandoItemCardapioForNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve retornar null ao converter uma entidade nula para domínio")
    void deveRetornarNullQuandoJpaEntityForNula() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando cardápio for nulo")
    void deveRetornarListaVaziaQuandoCardapioForNulo() {

        List<ItemCardapio> resultado = mapper.toDomainList(null);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

}
