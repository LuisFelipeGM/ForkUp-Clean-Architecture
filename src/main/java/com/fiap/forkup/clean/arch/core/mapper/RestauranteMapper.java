package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.CreateRestauranteRequest;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponsePartial;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestauranteMapper {

    private EnderecoMapper enderecoMapper;

    public Restaurante requestToDomain(CreateRestauranteRequest createRestauranteRequest) {
        return new Restaurante(
                null,
                createRestauranteRequest.nome(),
                createRestauranteRequest.tipoCozinha(),
                createRestauranteRequest.horarioFuncionamento(),
                enderecoMapper.toDomain(createRestauranteRequest.endereco()),
                createRestauranteRequest.gerenteId()
        );
    }

    public RestauranteResponseFull domainToDtoFull(Restaurante restaurante, String nomeGerente) {
        return new RestauranteResponseFull(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                nomeGerente,
                enderecoMapper.doaminToDto(restaurante.getEndereco())
        );
    }

    public RestauranteResponsePartial domainToDtoPartial(Restaurante restaurante) {
        return new RestauranteResponsePartial(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento()
        );
    }

}
