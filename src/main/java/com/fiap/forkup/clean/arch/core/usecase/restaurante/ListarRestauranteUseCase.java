package com.fiap.forkup.clean.arch.core.usecase.restaurante;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.Pagina;
import com.fiap.forkup.clean.arch.core.dto.PaginacaoRequest;
import com.fiap.forkup.clean.arch.core.gateway.RestauranteGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListarRestauranteUseCase {

    private final RestauranteGateway restauranteGateway;

    public Pagina<Restaurante> execute(PaginacaoRequest paginacaoRequest) {
        return restauranteGateway
                .listar(paginacaoRequest);
    }

}
