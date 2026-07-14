package com.fiap.forkup.clean.arch.mappers;

import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.EnderecoEntityMapper;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.TipoUsuarioEntityMapper;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.UsuarioEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes unitários para a classe UsuarioEntityMapper")
public class UsuarioEntityMapperTest {

    private UsuarioEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UsuarioEntityMapper(
                new TipoUsuarioEntityMapper(),
                new EnderecoEntityMapper()
        );
    }

    @Test
    @DisplayName("Deve retornar null ao converter um domínio nulo para entidade")
    void deveRetornarNullQuandoUsuarioForNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve retornar null ao converter uma entidade nula para domínio")
    void deveRetornarNullQuandoJpaEntityForNula() {
        assertNull( mapper.toDomain(null));
    }

}
