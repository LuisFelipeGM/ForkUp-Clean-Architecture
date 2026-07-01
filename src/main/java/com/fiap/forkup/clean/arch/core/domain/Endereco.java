package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.EnderecoInvalidoException;
import lombok.Getter;

@Getter
public class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String cep;

    public Endereco(String logradouro, String numero, String complemento, String cidade, String cep) {
        validarLogradouro(logradouro);
        validarNumero(numero);
        validarCidade(cidade);
        validarCep(cep);

        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento =  complemento != null ? complemento.trim() : null;
        this.cidade = cidade;
        this.cep = normalizarCep(cep);
    }

    private static void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank())
            throw new EnderecoInvalidoException("Logradouro é obrigatório.");
    }

    private static void validarNumero(String numero) {
        if (numero == null || numero.isBlank())
            throw new EnderecoInvalidoException("Número é obrigatório.");
    }

    private static void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank())
            throw new EnderecoInvalidoException("Cidade é obrigatória.");
    }

    private static void validarCep(String cep) {
        if (cep == null || !cep.replaceAll("\\D", "").matches("\\d{8}"))
            throw new EnderecoInvalidoException("CEP inválido.");
    }

    private static String normalizarCep(String cep) {
        return cep.replaceAll("\\D", "");
    }
}
