package com.fiap.forkup.clean.arch.core.domain;

import com.fiap.forkup.clean.arch.core.exception.UsuarioInvalidoException;
import lombok.Getter;

import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Usuario {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern SENHA_FORTE_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$");

    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;
    private Endereco endereco;

    public Usuario(UUID id, String nome, String email, String login, String senha, TipoUsuario tipoUsuario, Endereco endereco) {
        validarNome(nome);
        validarEmail(email);
        validarLogin(login);
        validarSenha(senha);
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.endereco = endereco;
    }

    public void atualizarUsuario(String nome, String email, String login, Endereco endereco) {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.endereco = endereco;
    }

    public void atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new UsuarioInvalidoException("Nome é obrigatório.");

        if (nome.trim().length() < 2)
            throw new UsuarioInvalidoException("Nome deve ter ao menos 2 caracteres.");
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank())
            throw new UsuarioInvalidoException("Email é obrigatório.");

        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new UsuarioInvalidoException("Email inválido.");
    }

    private void validarLogin(String login) {
        if (login == null || login.isBlank())
            throw new UsuarioInvalidoException("Login é obrigatório.");

        if (login.contains(" "))
            throw new UsuarioInvalidoException("Login não pode conter espaços.");

        if (login.trim().length() < 3)
            throw new UsuarioInvalidoException("Login deve ter ao menos 3 caracteres.");
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.isBlank())
            throw new UsuarioInvalidoException("Senha é obrigatória.");

        if (!SENHA_FORTE_PATTERN.matcher(senha).matches())
            throw new UsuarioInvalidoException("Senha deve ter ao menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.");
    }

}

