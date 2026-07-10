package com.fiap.forkup.clean.arch.controller;

import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioResponse;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.TipoUsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.TipoUsuarioVO;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tipo Usuário Controller")
public class TipoUsuarioControllerIT extends BaseControllerIT{

    private static final String BASE_URL = "/api/tipo-usuario";

    @Nested
    @DisplayName("Testes com cenário de sucesso")
    class Sucess {

        @Test
        @DisplayName("Deve listar tipos de usuários com sucesso")
        void testListarTiposUsuarios() {

            ResponseEntity<List<TipoUsuarioResponse>> response = restTemplate
                    .exchange(url(BASE_URL), HttpMethod.GET, null, new ParameterizedTypeReference<List<TipoUsuarioResponse>>() {});

                assertNotNull(response.getBody());
                assertFalse(response.getBody().isEmpty());
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
        }

        @Test
        @DisplayName("Deve buscar TipoUsuario por ID com sucesso")
        void testBuscarTipoUsuarioPorId() {
            ResponseEntity<TipoUsuarioResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + cliente.getId()), HttpMethod.GET, null, TipoUsuarioResponse.class);

            assertNotNull(response.getBody());

            TipoUsuarioResponse dto = response.getBody();
            assertEquals(cliente.getId(), dto.id());
            assertEquals(cliente.getDescricao(), dto.descricao());
        }

        @Test
        @DisplayName("Deve criar um TipoUsuario com sucesso")
        void testCriarTipoUsuario() {
            TipoUsuarioVO vo = new TipoUsuarioVO("Suporte");

            ResponseEntity<UUID> response = restTemplate.exchange(
                    url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), UUID.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            tipoUsuarioRepository.findById(response.getBody()).ifPresentOrElse(
                    entity -> assertEquals("Suporte", entity.getDescricao()),
                    () -> fail("TipoUsuario não encontrado no banco de dados"));
        }


        @Test
        @DisplayName("Deve atualizar um TipoUsuario com sucesso")
        void testAtualizarTipoUsuario() {
            TipoUsuarioJpaEntity tipoUsuario = createTipoUsuario(UUID.randomUUID(), "Suporte");

            String novaDescricao = "Suporte Especializado";
            TipoUsuarioVO vo = new TipoUsuarioVO(novaDescricao);

            ResponseEntity<TipoUsuarioResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + tipoUsuario.getId()), HttpMethod.PUT, new HttpEntity<>(vo), TipoUsuarioResponse.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            TipoUsuarioResponse dto = response.getBody();
            assertEquals(tipoUsuario.getId(), dto.id());
            assertEquals(novaDescricao, dto.descricao());
        }

        @Test
        @DisplayName("Deve deletar um TipoUsuario com sucesso")
        void testDeletarTipoUsuario() {
            TipoUsuarioJpaEntity tipoUsuario = createTipoUsuario(UUID.randomUUID(), "Tipo Exclusão");

            ResponseEntity<Void> response = restTemplate.exchange(
                    url(BASE_URL + "/" + tipoUsuario.getId()), HttpMethod.DELETE, new HttpEntity<>(org.springframework.http.HttpEntity.EMPTY), Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertFalse(tipoUsuarioRepository.existsById(tipoUsuario.getId()));
        }

    }

    @Nested
    @DisplayName("Testes com cenários de erro")
    class Erros {

        @Test
        @DisplayName("Deve retornar 404 ao buscar TipoUsuario inexistente")
        void testRetornar404AoBuscarTipoUsuarioInexistente() {
            UUID idInexistente = UUID.randomUUID();

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + idInexistente), HttpMethod.GET, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Tipo Usuário não encontrado", error.message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar criar TipoUsuario com descrição existente")
        void testRetornar409AoTentarCriarTipoUsuarioExistente() {
            TipoUsuarioJpaEntity tipoUsuario = createTipoUsuario(UUID.randomUUID(), "Suporte");
            TipoUsuarioVO vo = new TipoUsuarioVO(tipoUsuario.getDescricao());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Tipo Usuário já cadastrado: " + tipoUsuario.getDescricao(), error.message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar atualizar a descrição de um TipoUsuario inexistente")
        void testRetornar404AoTentarAtualizarTipoUsuarioInexistente() {
            TipoUsuarioVO vo = new TipoUsuarioVO("Suporte");

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + UUID.randomUUID(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Tipo Usuário não encontrado", error.message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar atualizar a descrição de um TipoUsuario para uma já existente")
        void testRetornar409AoTentarAtualizarTipoUsuarioParaUmExistente() {
            TipoUsuarioJpaEntity tipoUsuario = createTipoUsuario(UUID.randomUUID(), "Suporte");
            TipoUsuarioVO vo = new TipoUsuarioVO(cliente.getDescricao());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + tipoUsuario.getId(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Tipo Usuário já cadastrado: " + vo.getDescricao(), error.message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar excluir um TipoUsuario inexistente")
        void testRetornar404AoTentarExcluirTipoUsuarioInexistente() {

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + UUID.randomUUID(), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Tipo Usuário não encontrado", error.message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar excluir um TipoUsuario vinculado a um Usuário")
        void testRetornar400AoTentarExcluirTipoUsuarioVinculado() {
            TipoUsuarioJpaEntity tipoUsuario = createTipoUsuario(UUID.randomUUID(), "Tipo Vinculado");
            EnderecoJpaEntity endereco = createEndereco(UUID.randomUUID(), "Rua Teste", "123", "Ap 114", "Cidade Teste", "12345-678");
            createUsuario(UUID.randomUUID(), "John Doe", "john.doe@example.com", "JohnDoe123", "password", tipoUsuario, endereco);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + tipoUsuario.getId(), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            ErrorResponse error = response.getBody();
            assertEquals("Não é possível excluir o tipo de usuário pois existem usuários associados a ele.", error.message());
        }

    }

    @BeforeEach
    void beforeEach() {
        deleteAll();
        cargaInicial();
    }

}
