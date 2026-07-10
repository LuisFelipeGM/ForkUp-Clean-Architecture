package com.fiap.forkup.clean.arch.controller;

import com.fiap.forkup.clean.arch.config.TestConfig;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.RestauranteJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.TipoUsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.UsuarioJpaEntity;
import com.fiap.forkup.clean.arch.infra.persistence.jpa.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public abstract class BaseControllerIT extends AbstractControllerIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected EnderecoRepository enderecoRepository;

    @Autowired
    protected RestauranteRepository restauranteRepository;

    @Autowired
    protected ItemCardapioRepository itemCardapioRepository;

    @LocalServerPort
    protected int port;


    protected TipoUsuarioJpaEntity dono;

    protected TipoUsuarioJpaEntity cliente;

    protected String url(String path) {
        return "http://localhost:" + port + path;
    }

    protected void deleteAll() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        enderecoRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
    }

    protected void cargaInicial() {
        dono = createTipoUsuario(UUID.fromString("2f8c9a0b-2d3e-4b6c-9a0b-2d3e4b6c9a0b"), "Dono do Restaurante");
        cliente = createTipoUsuario(UUID.fromString("1e7b8f9e-1c2d-4a5b-8f9e-1c2d4a5b8f9e"), "Cliente");
    }

    protected TipoUsuarioJpaEntity builderTipoUsuario(UUID id, String descricao) {
        return TipoUsuarioJpaEntity.builder()
                .id(id)
                .descricao(descricao)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    protected TipoUsuarioJpaEntity createTipoUsuario(UUID id, String descricao) {
        TipoUsuarioJpaEntity tipoUsuario = builderTipoUsuario(id, descricao);
        tipoUsuario = tipoUsuarioRepository.save(tipoUsuario);
        return tipoUsuario;
    }

    protected EnderecoJpaEntity builderEndereco(UUID id, String logradouro, String numero, String complemento, String cidade, String cep) {
        return EnderecoJpaEntity.builder()
                .id(id)
                .logradouro(logradouro)
                .numero(numero)
                .complemento(complemento)
                .cidade(cidade)
                .cep(cep)
                .build();
    }

    protected EnderecoJpaEntity createEndereco(UUID id, String logradouro, String numero, String complemento, String cidade, String cep) {
        EnderecoJpaEntity endereco = builderEndereco(id, logradouro, numero, complemento, cidade, cep);
        endereco = enderecoRepository.save(endereco);
        return endereco;
    }

    protected EnderecoJpaEntity createEnderecoCompleto() {
        return createEndereco(UUID.randomUUID(), "Rua Teste", "123", "Ap 114", "Cidade Teste", "12345-678");
    }

    protected UsuarioJpaEntity builderUsuario(UUID id, String nome, String email, String login, String senha, TipoUsuarioJpaEntity tipoUsuario, EnderecoJpaEntity endereco) {
        return UsuarioJpaEntity.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .login(login)
                .senha(senha)
                .tipoUsuario(tipoUsuario)
                .endereco(endereco)
                .build();
    }

    protected UsuarioJpaEntity createUsuario(UUID id, String nome, String email, String login, String senha, TipoUsuarioJpaEntity tipoUsuario, EnderecoJpaEntity endereco) {
        UsuarioJpaEntity usuario = builderUsuario(id, nome, email, login, senha, tipoUsuario, endereco);
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    protected RestauranteJpaEntity builderRestaurante(UUID id, String nome, String tipoCozinha, String horarioFuncionamento, EnderecoJpaEntity endereco, UsuarioJpaEntity dono) {
        return RestauranteJpaEntity.builder()
                .id(id)
                .nome(nome)
                .tipoCozinha(tipoCozinha)
                .horarioFuncionamento(horarioFuncionamento)
                .endereco(endereco)
                .dono(dono)
                .build();
    }

    protected RestauranteJpaEntity createRestaurante(UUID id, String nome, String tipoCozinha, String horarioFuncionamento,UsuarioJpaEntity dono, EnderecoJpaEntity endereco) {
        RestauranteJpaEntity restaurante = builderRestaurante(id, nome, tipoCozinha, horarioFuncionamento, endereco, dono);
        restaurante = restauranteRepository.save(restaurante);
        return restaurante;
    }

}

