package com.fiap.forkup.clean.arch.core.dto;

import com.fiap.forkup.clean.arch.core.domain.Endereco;

public record EnderecoResponse(
        String logradouro,
        String numero,
        String complemento,
        String cidade,
        String cep
) {

    public static EnderecoResponse fromDomain(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getCep()
        );
    }

}
