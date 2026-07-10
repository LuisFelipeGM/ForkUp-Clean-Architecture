package com.fiap.forkup.clean.arch.controller;

import com.fiap.forkup.clean.arch.core.dto.AtualizarUsuarioInput;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.UsuarioResponseFull;
import com.fiap.forkup.clean.arch.core.dto.UsuarioResponsePartial;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.EnderecoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioAlterarTipoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioCreateVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioUpdateVO;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Usuário Controller")
public class UsuarioControllerIT extends BaseControllerIT {

    private static final String BASE_URL = "/api/usuario";
    @Nested
    @DisplayName("Testes com cenário de sucesso")
    class Sucess {

        @Test
        @DisplayName("Deve listar usuários com sucesso")
        void testListarUsuarioPaginado() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());

            ResponseEntity<Pagina<UsuarioResponsePartial>> response = restTemplate.exchange(
                    url(BASE_URL + "?page=0&size=10"), HttpMethod.GET, null, new ParameterizedTypeReference<Pagina<UsuarioResponsePartial>>() {});

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            Pagina<UsuarioResponsePartial> pagina = response.getBody();
            assertEquals(1, pagina.totalElementos());
            assertNotNull(pagina.conteudo());
            assertEquals(usuario.getId(), pagina.conteudo().getFirst().id());
            assertEquals(usuario.getNome(), pagina.conteudo().getFirst().nome());
            assertEquals(usuario.getEmail(), pagina.conteudo().getFirst().email());
        }

        @Test
        @DisplayName("Deve buscar usuário por ID com sucesso")
        void testBuscarUsuarioPorId() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            ResponseEntity<UsuarioResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/" + usuario.getId()), HttpMethod.GET, null, UsuarioResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            UsuarioResponseFull dto = response.getBody();

            assertEquals(usuario.getId(), dto.id());
            assertEquals(usuario.getNome(), dto.nome());
            assertEquals(usuario.getEmail(), dto.email());
        }

        @Test
        @DisplayName("Deve criar um usuário com sucesso")
        void testCriarUsuario() {
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioCreateVO vo = new UsuarioCreateVO("João Silva", "joao@gmail.com", "joao.silva", "SenhaForte123@", cliente.getId(), enderecoVO);

            ResponseEntity<UUID> response = restTemplate.exchange(url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), UUID.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            usuarioRepository.findById(response.getBody()).ifPresentOrElse(
                    entity -> assertEquals(vo.getNome(), entity.getNome()),
                    () -> Assertions.fail("Usuário não encontrado no banco de dados"));
        }

        @Test
        @DisplayName("Deve atualizar um usuário com sucesso")
        void testAtualizarUsuario() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());

            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "456", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioUpdateVO vo = new UsuarioUpdateVO("Lucas Mendes", "lucas@gmail.com", "LucaoMDS", enderecoVO);

            ResponseEntity<UsuarioResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/" + usuario.getId()), HttpMethod.PUT, new HttpEntity<>(vo), UsuarioResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            UsuarioResponseFull dto = response.getBody();

            assertEquals(usuario.getId(), dto.id());
            assertEquals(vo.getNome(), dto.nome());
            assertEquals(vo.getEmail(), dto.email());
            assertEquals(vo.getLogin(), dto.login());
        }

        @Test
        @DisplayName("Deve alterar o tipo do usuário com sucesso")
        void testAlterarTipoUsuario() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            UsuarioAlterarTipoVO vo = new UsuarioAlterarTipoVO(dono.getId());

            ResponseEntity<UsuarioResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/alterar-tipo/" + usuario.getId()), HttpMethod.PUT, new HttpEntity<>(vo), UsuarioResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().tipoUsuario());
            assertEquals(dono.getId(), response.getBody().tipoUsuario().id());
        }

        @Test
        @DisplayName("Deve deletar um usuário com sucesso")
        void deletarUsuario() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());

            ResponseEntity<Void> response = restTemplate.exchange(
                    url(BASE_URL + "/" + usuario.getId()), HttpMethod.DELETE, null, Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertFalse(usuarioRepository.existsById(usuario.getId()));
        }

    }

    @Nested
    @DisplayName("Testes com cenário de erro")
    class Error {

        @Test
        @DisplayName("Deve retornar 404 ao buscar usuário inexistente")
        void testRetornar404AoBuscarUsuarioInexistente() {
            UUID idInexistente = UUID.randomUUID();

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + idInexistente), HttpMethod.GET, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar criar Usuário com Tipo de Usuário inexistente")
        void testRetornar404AoTentarCriarUsuarioComTipoInexistente() {
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioCreateVO vo = new UsuarioCreateVO("João Silva", "joao@gmail.com", "joao.silva", "SenhaForte123@", UUID.randomUUID(), enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Tipo Usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar criar Usuário com Email já Existente")
        void testRetornar409AoTentarCriarUsuarioComEmailExistente() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioCreateVO vo = new UsuarioCreateVO("João Silva", "joao@gmail.com", "joao.silva", "SenhaForte123@", UUID.randomUUID(), enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Email já cadastrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar criar Usuário com Login já Existente")
        void testRetornar409AoTentarCriarUsuarioComLoginExistente() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioCreateVO vo = new UsuarioCreateVO("João Silva", "joao@gmail.com", "JoaoSilvaLA", "SenhaForte123@", UUID.randomUUID(), enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Login já cadastrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar atualizar Usuário inexistente")
        void testRetornar404AoTentarAtualizarUsuarioInexistente() {
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "456", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioUpdateVO vo = new UsuarioUpdateVO("Lucas Mendes", "lucas@gmail.com", "LucaoMDS", enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + UUID.randomUUID(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar atualizar Usuário com Email já Cadastrado")
        void testRetornar409AoTentarAtualizarUsuarioComEmailExistente() {
            createUsuario(UUID.randomUUID(), "Luís Felipe", "lfgm@gmail.com",
                    "LFGM", "SenhaForte123@", cliente, createEnderecoCompleto());

            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "456", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioUpdateVO vo = new UsuarioUpdateVO("Lucas Mendes", "lfgm@gmail.com", "LucaoMDS", enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + usuario.getId(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Já existe um usuário com este email", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 409 ao tentar atualizar Usuário com Login já Cadastrado")
        void testRetornar409AoTentarAtualizarUsuarioComLoginExistente() {
            createUsuario(UUID.randomUUID(), "Luís Felipe", "lfgm@gmail.com",
                    "LFGM", "SenhaForte123@", cliente, createEnderecoCompleto());

            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "João Silva", "joao.silva@gmail.com",
                    "JoaoSilvaLA", "SenhaForte123@", cliente, createEnderecoCompleto());
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "456", "Apto 101", "Cidade Exemplo", "12345-678");
            UsuarioUpdateVO vo = new UsuarioUpdateVO("Lucas Mendes", "luca.mendes@gmail.com", "LFGM", enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/" + usuario.getId(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Já existe um usuário com este login", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar alterar Tipo Usuário de um Usuário inexistente")
        void testRetornar404AoTentarAlterarTipoUsuarioDeUmUsuarioInexistente() {
            UsuarioAlterarTipoVO vo = new UsuarioAlterarTipoVO(dono.getId());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/alterar-tipo/" + UUID.randomUUID(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar alterar Tipo Usuário Inexistente de um Usuário")
        void testRetornar404AoTentarAlterarTipoUsuarioInexistenteDeUmUsuario() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "Luís Felipe", "lfgm@gmail.com",
                    "LFGM", "SenhaForte123@", cliente, createEnderecoCompleto());
            UsuarioAlterarTipoVO vo = new UsuarioAlterarTipoVO(UUID.randomUUID());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/alterar-tipo/" + usuario.getId(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Tipo de usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar alterar Tipo Usuário de um Usuário com Restaurante Vinculado")
        void testRetornar400AoTentarAlterarTipoUsuarioDeUmUsuarioComRestauranteVinculado() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "Luís Felipe", "lfgm@gmail.com",
                    "LFGM", "SenhaForte123@", dono, createEnderecoCompleto());
            createRestaurante(UUID.randomUUID(), "Restaurante Teste", "Mexicano", "10:00 - 19:00", usuario, createEnderecoCompleto());
            UsuarioAlterarTipoVO vo = new UsuarioAlterarTipoVO(cliente.getId());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL) + "/alterar-tipo/" + usuario.getId(), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Não é possível alterar o tipo do usuário pois existem restaurantes vinculados a ele.", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar excluir um Usuário inexistente")
        void testRetornar404AoTentarExcluirUsuarioInexistente() {
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID()), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Usuário não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar excluir um Usuário com Restaurante Vinculado")
        void testRetornar400AoTentarExcluirUsuarioComRestauranteVinculado() {
            UsuarioJpaEntity usuario = createUsuario(UUID.randomUUID(), "Luís Felipe", "lfgm@gmail.com",
                    "LFGM", "SenhaForte123@", dono, createEnderecoCompleto());
            createRestaurante(UUID.randomUUID(), "Restaurante Teste", "Mexicano", "10:00 - 19:00", usuario, createEnderecoCompleto());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + usuario.getId()), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Não é possível excluir o usuário pois existem restaurantes vinculados a ele.", response.getBody().message());
        }

    }

    @BeforeEach
    void beforeEach() {
        deleteAll();
        cargaInicial();
    }

}
