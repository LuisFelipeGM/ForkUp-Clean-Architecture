package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.PaginacaoRequest;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponsePartial;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    private final RestauranteMapper restauranteMapper;

    public Pagina<RestauranteResponsePartial> execute(PaginacaoRequest paginacaoRequest) {
        return restauranteGateway
                .listar(paginacaoRequest)
                .map(restauranteMapper::domainToDtoPartial);
    }

}
