package com.fiap.forkup.clean.arch.infra.web.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateVO {

    @NotNull( message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email deve ser valido")
    private String email;

    @NotNull(message = "Login é obrigatório")
    private String login;

    @NotNull(message = "Senha é obrigatória no cadastro")
    private String senha;

    @NotNull(message = "Tipo de usuário é obrigatório")
    private UUID tipoUsuario;

    @NotNull(message = "O Endereço é obrigatório")
    private EnderecoVO endereco;

}
