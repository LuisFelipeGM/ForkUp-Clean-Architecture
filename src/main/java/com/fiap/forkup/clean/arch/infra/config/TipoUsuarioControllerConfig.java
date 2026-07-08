package com.fiap.forkup.clean.arch.infra.config;

import com.fiap.forkup.clean.arch.core.controller.TipoUsuarioController;
import com.fiap.forkup.clean.arch.core.mapper.TipoUsuarioMapper;
import com.fiap.forkup.clean.arch.core.usecase.tipousuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TipoUsuarioControllerConfig {

    @Bean
    public TipoUsuarioController tipoUsuarioController(
            ListarTiposUsuarioUseCase listarTiposUsuarioUseCase,
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            CriarTipoUsuarioUseCase criarTipoUsuarioUseCase,
            AlterarDescricaoTipoUsuarioUseCase alterarDescricaoTipoUsuarioUseCase,
            DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase,
            TipoUsuarioMapper tipoUsuarioMapper) {
        return new TipoUsuarioController(
                listarTiposUsuarioUseCase,
                buscarTipoUsuarioPorIdUseCase,
                criarTipoUsuarioUseCase,
                alterarDescricaoTipoUsuarioUseCase,
                deletarTipoUsuarioUseCase,
                tipoUsuarioMapper);
    }

}
