package com.fiap.forkup.clean.arch.core.mapper;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.dto.EnderecoRequest;
import com.fiap.forkup.clean.arch.core.dto.EnderecoResponse;

import java.util.UUID;

public class EnderecoMapper {

    public Endereco toDomain(EnderecoRequest request) {
        if(request == null) return null;

        return new Endereco(
                UUID.randomUUID(),
                request.logradouro(),
                request.numero(),
                request.complemento(),
                request.cidade(),
                request.cep()
        );
    }

    public EnderecoResponse doaminToDto(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getCep()
        );
    }

}
