package com.fiap.forkup.clean.arch.infra.config;

import com.fiap.forkup.clean.arch.core.controller.UsuarioController;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.UsuarioMapper;
import com.fiap.forkup.clean.arch.core.usecase.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UsuarioControllerConfig {

    @Bean
    public UsuarioController usuarioController(
            ListarUsuariosUseCase listarUsuariosUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            CriarUsuarioUseCase criarUsuarioUseCase,
            AlterarUsuarioUseCase alterarUsuarioUseCase,
            AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase,
            DeletarUsuarioUseCase deletarUsuarioUseCase,
            UsuarioMapper usuarioMapper,
            EnderecoMapper enderecoMapper) {
        return new UsuarioController(
                listarUsuariosUseCase,
                buscarUsuarioPorIdUseCase,
                criarUsuarioUseCase,
                alterarUsuarioUseCase,
                alterarTipoDoUsuarioUseCase,
                deletarUsuarioUseCase,
                usuarioMapper,
                enderecoMapper);
    }

}
