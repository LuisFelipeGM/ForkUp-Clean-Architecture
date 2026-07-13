package com.fiap.forkup.clean.arch.infra.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotNull(message = "Nome é obrigatório")
    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String nome;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email deve ser valido")
    @Schema(description = "Email do usuário", example = "joao.silva@example.com")
    private String email;

    @NotNull(message = "Login é obrigatório")
    @Schema(description = "Login do usuário", example = "joao.silva")
    private String login;

    @NotNull(message = "Senha é obrigatória no cadastro")
    @Schema(description = "Senha do usuário", example = "SenhaForte123@")
    private String senha;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @Schema(description = "ID do tipo de usuário", example = "5513b568-3fc9-4cc3-8fce-33c16dd67870")
    private UUID tipoUsuario;

    @NotNull(message = "O Endereço é obrigatório")
    @Schema(description = "Endereço do usuário")
    private EnderecoVO endereco;

}
