package com.fiap.forkup.clean.arch.infra.config;

import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.gateway.TipoUsuarioGateway;
import com.fiap.forkup.clean.arch.core.gateway.UsuarioGateway;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.*;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.*;
import com.fiap.forkup.clean.arch.core.usecase.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCaseConfig {

    private final UsuarioGateway usuarioGateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;
    private final RestauranteGateway restauranteGateway;

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase() {
        return new CriarUsuarioUseCase(usuarioGateway, tipoUsuarioGateway);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase() {
        return new ListarUsuariosUseCase(usuarioGateway);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase() {
        return new BuscarUsuarioPorIdUseCase(usuarioGateway);
    }

    @Bean
    public AlterarUsuarioUseCase alterarUsuarioUseCase() {
        return new AlterarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase() {
        return new DeletarUsuarioUseCase(usuarioGateway);
    }

    @Bean
    public AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase() {
        return new AlterarTipoDoUsuarioUseCase(usuarioGateway, tipoUsuarioGateway);
    }

    @Bean
    public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase() {
        return new CriarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public ListarTiposUsuarioUseCase listarTiposUsuarioUseCase() {
        return new ListarTiposUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase() {
        return new BuscarTipoUsuarioPorIdUseCase(tipoUsuarioGateway);
    }

    @Bean
    public AlterarDescricaoTipoUsuarioUseCase alterarDescricaoTipoUsuarioUseCase() {
        return new AlterarDescricaoTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase() {
        return new DeletarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    @Bean
    public CriarRestauranteUseCase criarRestauranteUseCase() {
        return new CriarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    @Bean
    public ListarRestauranteUseCase listarRestauranteUseCase() {
        return new ListarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase() {
        return new BuscarRestaurantePorIdUseCase(restauranteGateway);
    }

    @Bean
    public AlterarRestauranteUseCase alterarRestauranteUseCase() {
        return new AlterarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public DeletarRestauranteUseCase deletarRestauranteUseCase() {
        return new DeletarRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public AlterarDonoRestauranteUseCase alterarDonoRestauranteUseCase() {
        return new AlterarDonoRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    @Bean
    public AdicionarItemCardapioUseCase adicionarItemCardapioUseCase() {
        return new AdicionarItemCardapioUseCase(restauranteGateway);
    }

    @Bean
    public ListarCardapioRestauranteUseCase listarCardapioRestauranteUseCase() {
        return new ListarCardapioRestauranteUseCase(restauranteGateway);
    }

    @Bean
    public AtualizarItemCardapioUseCase atualizarItemCardapioUseCase() {
        return new AtualizarItemCardapioUseCase(restauranteGateway);
    }

    @Bean
    public RemoverItemCardapioUseCase removerItemCardapioUseCase() {
        return new RemoverItemCardapioUseCase(restauranteGateway);
    }
}
