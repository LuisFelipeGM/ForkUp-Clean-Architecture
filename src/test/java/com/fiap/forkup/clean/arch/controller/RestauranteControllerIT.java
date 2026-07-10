package com.fiap.forkup.clean.arch.controller;

import com.fiap.forkup.clean.arch.core.dto.ItemCardapioResponse;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponsePartial;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.ItemCardapioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.*;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Restaurante Controller")
public class RestauranteControllerIT extends BaseControllerIT {

    private static final String BASE_URL = "/api/restaurante";

    @Nested
    @DisplayName("Testes com cenário de sucesso")
    class Sucess {

        @Test
        @DisplayName("Deve listar restaurantes paginado com sucesso")
        void testListarRestaurantesPaginado() {
            UsuarioJpaEntity dono = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", dono, createEnderecoCompleto());

            ResponseEntity<Pagina<RestauranteResponsePartial>> response = restTemplate.exchange(
                    url(BASE_URL + "?page=0&size=10"), HttpMethod.GET, null, new ParameterizedTypeReference<Pagina<RestauranteResponsePartial>>() {});

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            Pagina<RestauranteResponsePartial> pagina = response.getBody();
            assertEquals(1, pagina.totalElementos());
            assertNotNull(pagina.conteudo());
            assertEquals(restaurante.getId(), pagina.conteudo().getFirst().id());
            assertEquals(restaurante.getNome(), pagina.conteudo().getFirst().nomeRestaurante());
            assertEquals(restaurante.getTipoCozinha(), pagina.conteudo().getFirst().tipoCozinha());
        }

        @Test
        @DisplayName("Deve buscar restaurantes por ID com sucesso")
        void testeBuscarRestaurantesPorId() {
            UsuarioJpaEntity dono = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", dono, createEnderecoCompleto());

            ResponseEntity<RestauranteResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId()), HttpMethod.GET, null, RestauranteResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            RestauranteResponseFull restauranteResponseFull = response.getBody();

            assertEquals(restaurante.getId(), restauranteResponseFull.id());
            assertEquals(restaurante.getNome(), restauranteResponseFull.nomeRestaurante());
            assertEquals(restaurante.getTipoCozinha(), restauranteResponseFull.tipoCozinha());
        }

        @Test
        @DisplayName("Deve criar restaurante com sucesso")
        void testCriarRestaurante() {
            UsuarioJpaEntity dono = createDono();
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            RestauranteCreateVO vo = new RestauranteCreateVO("Gendai", "Japonesa", "12:00 - 23:00", dono.getId(), enderecoVO);

            ResponseEntity<UUID> response = restTemplate.exchange(url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), UUID.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            restauranteRepository.findById(response.getBody()).ifPresentOrElse(
                    entity -> assertEquals(vo.getNome(), entity.getNome()),
                    () -> Assertions.fail("Restaurante não encontrado no banco de dados"));

        }

        @Test
        @DisplayName("Deve alterar restaurante com sucesso")
        void testAlterarRestaurante() {
            UsuarioJpaEntity dono = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", dono, createEnderecoCompleto());

            EnderecoVO enderecoVO = new EnderecoVO("Rua Alterada", "456", "Apto 202", "Cidade Alterada", "98765-432");
            RestauranteUpdateVO vo = new RestauranteUpdateVO("Gendai Alterado", "Asiática", "12:00 - 23:00", enderecoVO);

            ResponseEntity<RestauranteResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId()), HttpMethod.PUT, new HttpEntity<>(vo), RestauranteResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            RestauranteResponseFull dto = response.getBody();

            assertEquals(restaurante.getId(), dto.id());
            assertEquals(vo.getNome(), dto.nomeRestaurante());
            assertEquals(vo.getTipoCozinha(), dto.tipoCozinha());
        }

        @Test
        @DisplayName("Deve alterar dono do restaurante com sucesso")
        void testAlterarDonoRestaurante() {
            UsuarioJpaEntity donoAntigo = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", donoAntigo, createEnderecoCompleto());

            UsuarioJpaEntity novoDono = createUsuario(UUID.randomUUID(), "Novo Dono", "novo.dono@example.com", "novoDono", "SenhaForte123@", dono, createEnderecoCompleto());
            RestauranteAlterarDonoVO vo = new RestauranteAlterarDonoVO(novoDono.getId());

            ResponseEntity<RestauranteResponseFull> response = restTemplate.exchange(
                    url(BASE_URL + "/alterar-dono/" + restaurante.getId()), HttpMethod.PUT, new HttpEntity<>(vo), RestauranteResponseFull.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().nomeDono());
            assertEquals(novoDono.getNome(), response.getBody().nomeDono());
        }

        @Test
        @DisplayName("Deve deletar restaurante com sucesso")
        void testDeletarRestaurante() {
            UsuarioJpaEntity donoAntigo = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", donoAntigo, createEnderecoCompleto());

            ResponseEntity<Void> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId()), HttpMethod.DELETE, null, Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertFalse(restauranteRepository.existsById(restaurante.getId()));
        }

        @Test
        @DisplayName("Deve deletar restaurante com cardapio e o cardapio também deve ser deletado com sucesso")
        void testDeletarRestauranteComCardapio() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            ItemCardapioJpaEntity item = createItemCardapio(UUID.randomUUID(), "Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg", restaurante);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId()), HttpMethod.DELETE, null, Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertFalse(itemCardapioRepository.existsById(item.getId()));
        }

        @Test
        @DisplayName("Deve listar cardápio do restaurante com sucesso")
        void testListarCardapioDoRestaurante() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            createItemCardapio(UUID.randomUUID(), "Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg", restaurante);

            ResponseEntity<List<ItemCardapioResponse>> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId() + "/cardapio"), HttpMethod.GET, null, new ParameterizedTypeReference<List<ItemCardapioResponse>>() {});

            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
        }

        @Test
        @DisplayName("Deve criar item no cardápio do restaurante com sucesso")
        void testCriarItemNoCardapioDoRestaurante() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            ItemCardapioVO vo = new ItemCardapioVO("Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg");

            ResponseEntity<ItemCardapioResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId() + "/cardapio"), HttpMethod.POST, new HttpEntity<>(vo), ItemCardapioResponse.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().id());
            itemCardapioRepository.findById(response.getBody().id()).ifPresentOrElse(
                entity -> assertEquals(vo.getNome(), entity.getNome()),
                () -> fail("Item do cardápio não encontrado no banco de dados"));
        }

        @Test
        @DisplayName("Deve alterar item do cardápio do restaurante com sucesso")
        void testAtualizarItemDoCardapioDoRestaurante() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            ItemCardapioJpaEntity item = createItemCardapio(UUID.randomUUID(), "Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg", restaurante);

            ItemCardapioVO vo = new ItemCardapioVO("Sushi Atualizado", "Delicioso sushi atualizado", new BigDecimal("39.90"), true, "http://example.com/sushi_atualizado.jpg");

            ResponseEntity<ItemCardapioResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId() + "/cardapio/" + item.getId()), HttpMethod.PUT, new HttpEntity<>(vo), ItemCardapioResponse.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());

            ItemCardapioResponse dto = response.getBody();

            assertEquals(item.getId(), dto.id());
            assertEquals(vo.getNome(), dto.nome());
            assertEquals(vo.getDescricao(), dto.descricao());
            assertEquals(vo.getPreco(), dto.preco());
        }

        @Test
        @DisplayName("Deve excluir item do cardápio do restaurante com sucesso")
        void testExcluirItemDoCardapioDoRestaurante() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            ItemCardapioJpaEntity item = createItemCardapio(UUID.randomUUID(), "Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg", restaurante);

            ResponseEntity<Void> response = restTemplate.exchange(
                    url(BASE_URL + "/" + restaurante.getId() + "/cardapio/" + item.getId()), HttpMethod.DELETE, null, Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertFalse(itemCardapioRepository.existsById(item.getId()));
        }

    }

    @Nested
    @DisplayName("Testes com cenário de erro")
    class Erros {

        @Test
        @DisplayName("Deve retornar 404 ao buscar restaurante inexistente")
        void testRetornar404AoBuscarRestauranteInexistente() {
            UUID idInexistente = UUID.randomUUID();

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + idInexistente), HttpMethod.GET, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar criar restaurante com dono inexistente")
        void testRetornar404AoTentarCriarUmRestauranteComDonoInexistente() {
            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            RestauranteCreateVO vo = new RestauranteCreateVO("Gendai", "Japonesa", "12:00 - 23:00", UUID.randomUUID(), enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Dono do Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar criar restaurante com dono já vinculado a outro restaurante")
        void testRetornar400AoTentarCriarUmRestauranteComDonoJaVinculado() {
            UsuarioJpaEntity donoVinculado = createDono();
            createRestaurante(UUID.randomUUID(), "Restaurante Existente", "Italiana", "10:00 - 22:00", donoVinculado, createEnderecoCompleto());

            EnderecoVO enderecoVO = new EnderecoVO("Rua Exemplo", "123", "Apto 101", "Cidade Exemplo", "12345-678");
            RestauranteCreateVO vo = new RestauranteCreateVO("Gendai", "Japonesa", "12:00 - 23:00", donoVinculado.getId(), enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(url(BASE_URL), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Não é possível criar o restaurante pois o dono já está vinculado a outro restaurante.", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar alterar restaurante inexistente")
        void testRetornar404AlterarRestauranteInexistente() {
            EnderecoVO enderecoVO = new EnderecoVO("Rua Alterada", "456", "Apto 202", "Cidade Alterada", "98765-432");
            RestauranteUpdateVO vo = new RestauranteUpdateVO("Gendai Alterado", "Asiática", "12:00 - 23:00", enderecoVO);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID()), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar alterar dono do restaurante inexistente")
        void testRetornar404AlterarDonoDeRestauranteInexistente() {
            UsuarioJpaEntity donoAntigo = createDono();
            createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", donoAntigo, createEnderecoCompleto());

            UsuarioJpaEntity novoDono = createUsuario(UUID.randomUUID(), "Novo Dono", "novo.dono@example.com", "novoDono", "SenhaForte123@", dono, createEnderecoCompleto());
            RestauranteAlterarDonoVO vo = new RestauranteAlterarDonoVO(novoDono.getId());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/alterar-dono/" + UUID.randomUUID()), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar alterar para dono inexistente")
        void testRetornar404AlterarDonoInexistente() {
            UsuarioJpaEntity donoAntigo = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", donoAntigo, createEnderecoCompleto());

            createUsuario(UUID.randomUUID(), "Novo Dono", "novo.dono@example.com", "novoDono", "SenhaForte123@", dono, createEnderecoCompleto());
            RestauranteAlterarDonoVO vo = new RestauranteAlterarDonoVO(UUID.randomUUID());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/alterar-dono/" + restaurante.getId()), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Dono do Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 400 ao tentar alterar para dono já vinculado a outro restaurante")
        void testRetornar400AlterarDonoVinculado() {
            UsuarioJpaEntity donoAntigo = createDono();
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", donoAntigo, createEnderecoCompleto());

            UsuarioJpaEntity novoDono = createUsuario(UUID.randomUUID(), "Novo Dono", "novo.dono@example.com", "novoDono", "SenhaForte123@", dono, createEnderecoCompleto());
            createRestaurante(UUID.randomUUID(), "Restaurante Existente", "Italiana", "10:00 - 22:00", novoDono, createEnderecoCompleto());
            RestauranteAlterarDonoVO vo = new RestauranteAlterarDonoVO(novoDono.getId());

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/alterar-dono/" + restaurante.getId()), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Dono do Restaurante já está vinculado a outro restaurante.", response.getBody().message());
        }

        @Test
        @DisplayName("Deve retornar 404 ao tentar deletar restaurante inexistente")
        void testRetornar404DeletarRestauranteInexistente() {
            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID()), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve Retornar 404 ao tentar listar cardápio do restaurante inexistente")
        void testRetornar404ListarCardapioDoRestauranteInexistente() {

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID() + "/cardapio"), HttpMethod.GET, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve Retornar 404 ao tentar criar item no cardápio do restaurante inexistente")
        void testRetornar404CriarItemNoCardapioDoRestauranteInexistente() {
            ItemCardapioVO vo = new ItemCardapioVO("Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg");

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID() + "/cardapio"), HttpMethod.POST, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve Retornar 404 ao tentar atualizar item do cardápio do restaurante inexistente")
        void testRetornar404AtualizarItemDoCardapioDoRestauranteInexistente() {

            ItemCardapioVO vo = new ItemCardapioVO("Sushi Atualizado", "Delicioso sushi atualizado", new BigDecimal("39.90"), true, "http://example.com/sushi_atualizado.jpg");

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID() + "/cardapio/" + UUID.randomUUID()), HttpMethod.PUT, new HttpEntity<>(vo), ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

        @Test
        @DisplayName("Deve Retornar 404 ao tentar excluir item do cardápio do restaurante inexistente")
        void testRetornar404ExcluirItemDoCardapioDoRestauranteInexistente() {
            RestauranteJpaEntity restaurante = createRestaurante(UUID.randomUUID(), "Gendai", "Japonesa", "12:00 - 23:00", createDono(), createEnderecoCompleto());
            ItemCardapioJpaEntity item = createItemCardapio(UUID.randomUUID(), "Sushi", "Delicioso sushi", new BigDecimal("29.90"), true, "http://example.com/sushi.jpg", restaurante);

            ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                    url(BASE_URL + "/" + UUID.randomUUID() + "/cardapio/" + UUID.randomUUID()), HttpMethod.DELETE, null, ErrorResponse.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Restaurante não encontrado", response.getBody().message());
        }

    }

    @BeforeEach
    void beforeEach() {
        deleteAll();
        cargaInicial();
    }


}
