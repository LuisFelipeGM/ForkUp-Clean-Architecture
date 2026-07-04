package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.EnderecoInvalidoException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Endereco {

    private UUID id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String cep;

    public Endereco(UUID id, String logradouro, String numero, String complemento, String cidade, String cep) {
        validarLogradouro(logradouro);
        validarNumero(numero);
        validarCidade(cidade);
        validarCep(cep);

        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento =  complemento != null ? complemento.trim() : null;
        this.cidade = cidade;
        this.cep = normalizarCep(cep);
    }

    private void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank())
            throw new EnderecoInvalidoException("Logradouro é obrigatório.");
    }

    private void validarNumero(String numero) {
        if (numero == null || numero.isBlank())
            throw new EnderecoInvalidoException("Número é obrigatório.");
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank())
            throw new EnderecoInvalidoException("Cidade é obrigatória.");
    }

    private void validarCep(String cep) {
        if (cep == null || !cep.replaceAll("\\D", "").matches("\\d{8}"))
            throw new EnderecoInvalidoException("CEP inválido.");
    }

    private String normalizarCep(String cep) {
        return cep.replaceAll("\\D", "");
    }
}
