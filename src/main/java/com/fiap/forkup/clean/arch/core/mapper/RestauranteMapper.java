package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.RestauranteRequestCreate;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.core.dto.RestauranteResponsePartial;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RestauranteMapper {

    private EnderecoMapper enderecoMapper;

    public Restaurante requestToDomain(RestauranteRequestCreate restauranteRequestCreate) {
        return new Restaurante(
                UUID.randomUUID(),
                restauranteRequestCreate.nome(),
                restauranteRequestCreate.tipoCozinha(),
                restauranteRequestCreate.horarioFuncionamento(),
                enderecoMapper.toDomain(restauranteRequestCreate.endereco()),
                restauranteRequestCreate.donoId()
        );
    }

    public RestauranteResponseFull domainToDtoFull(Restaurante restaurante) {
        return new RestauranteResponseFull(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getNomeDono(),
                enderecoMapper.domainToDto(restaurante.getEndereco())
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
