package com.fiap.forkup.clean.arch.domain;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.TipoUsuario;
import com.fiap.forkup.clean.arch.core.domain.Usuario;
import com.fiap.forkup.clean.arch.core.exception.UsuarioInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a classe Usuario")
public class UsuarioTest {

    @Test
    @DisplayName("Deve criar um Usuario válido")
    void deveCriarUsuarioValido() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();
        UUID id = UUID.randomUUID();
        String nome = "Luís Felipe";
        String email = "luis.felipe@example.com";
        String login = "LuisFelipeGM";
        String senha = "SenhaForte123@";

        Usuario usuario = new Usuario(id, nome, email, login, senha, tipo, endereco);

        assertNotNull(usuario);
        assertEquals(id, usuario.getId());
        assertEquals(nome, usuario.getNome());
        assertEquals(email, usuario.getEmail());
        assertEquals(login, usuario.getLogin());
        assertEquals(senha, usuario.getSenha());
        assertEquals(tipo, usuario.getTipoUsuario());
        assertEquals(endereco, usuario.getEndereco());
    }

    @Test
    @DisplayName("Deve alterar os dados de um Usuario com sucesso")
    void deveAlterarUsuarioComSucesso() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();
        Usuario usuario = new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);

        String nomeNovo = "Marcos Almeida";
        String emailNovo = "marcos.almeida@gmail.com";
        String loginNovo = "MarcosAL";
        Endereco enderecoNovo = new Endereco(UUID.randomUUID(), "Rua B", "456", "Apto 202", "Rio de Janeiro", "98765-432");

        usuario.atualizarUsuario(nomeNovo, emailNovo, loginNovo, enderecoNovo);

        assertEquals(nomeNovo, usuario.getNome());
        assertEquals(emailNovo, usuario.getEmail());
        assertEquals(loginNovo, usuario.getLogin());
        assertEquals(enderecoNovo, usuario.getEndereco());

    }

    @Test
    @DisplayName("Deve alterar o tipo de um Usuario com sucesso")
    void deveAlterarTipoUsuarioComSucesso() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();
        Usuario usuario = new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);

        TipoUsuario tipoNovo = criarTipoUsuario("Dono do Restaurante");
        usuario.atualizarTipoUsuario(tipoNovo);

        assertEquals(tipoNovo, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void deveLancarExcecaoNomeNulo() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), null, "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for vazio")
    void deveLancarExcecaoNomeVazio() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "", "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome tiver menos de 2 caracteres")
    void deveLancarExcecaoNomeMenosDe2Caracteres() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "A", "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email for nulo")
    void deveLancarExcecaoEmailNulo() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", null, "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email for vazio")
    void deveLancarExcecaoEmailVazio() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o email for invalido")
    void deveLancarExcecaoEmailInvalido() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@invalid@//o", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login for nulo")
    void deveLancarExcecaoLoginNulo() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", null, "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login for vazio")
    void deveLancarExcecaoLoginVazio() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login tiver espaço")
    void deveLancarExcecaoLoginComEspaco() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipe GM", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login tiver menos de 3 caracteres")
    void deveLancarExcecaoLoginMenosDe3Caracteres() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "Lu", "SenhaForte123@", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for nulo")
    void deveLancarExcecaoSenhaNulo() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", null, tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for vazio")
    void deveLancarExcecaoSenhaVazio() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", "", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for fraca")
    void deveLancarExcecaoSenhaFraca() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", "senhafraca", tipo, endereco);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o nome for nulo")
    void deveLanarExcecaoAtualizarUsuarioNomeNulo() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
           usuario.atualizarUsuario(null, "luis.felipe@example.com", "LuisFelipeGM", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o nome for vazio")
    void deveLanarExcecaoAtualizarUsuarioNomeVazio() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("", "luis.felipe@example.com", "LuisFelipeGM", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o email for nulo")
    void deveLanarExcecaoAtualizarUsuarioEmailNulo() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", null, "LuisFelipeGM", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o email for vazio")
    void deveLanarExcecaoAtualizarUsuarioEmailVazio() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "", "LuisFelipeGM", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o email for inválido")
    void deveLanarExcecaoAtualizarUsuarioEmailInvalido() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "emai@linv@al/ido", "LuisFelipeGM", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o login for nulo")
    void deveLanarExcecaoAtualizarUsuarioLoginNulo() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "luis.felipe@example.com", null, enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o login for vazio")
    void deveLanarExcecaoAtualizarUsuarioLoginVazio() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "luis.felipe@example.com", "", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o login conter espaço")
    void deveLanarExcecaoAtualizarUsuarioLoginComEspaco() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "luis.felipe@example.com", "LuisFelipe GM ", enderecoNovo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando atualizar o usuário e o login tiver menos de 3 caracteres")
    void deveLanarExcecaoAtualizarUsuarioLoginComMenosDeTresCaracteres() {
        Usuario usuario = criarUsuarioCompleto();
        Endereco enderecoNovo = criarEndereco();

        assertThrows(UsuarioInvalidoException.class, () -> {
            usuario.atualizarUsuario("Luís Felipe", "luis.felipe@example.com", "Lu ", enderecoNovo);
        });
    }

    private Usuario criarUsuarioCompleto() {
        TipoUsuario tipo = criarTipoUsuario("Cliente");
        Endereco endereco = criarEndereco();
        return new Usuario(UUID.randomUUID(), "Luís Felipe", "luis.felipe@example.com", "LuisFelipeGM", "SenhaForte123@", tipo, endereco);
    }

    private TipoUsuario criarTipoUsuario(String descricao) {
        return new TipoUsuario(descricao);
    }

    private Endereco criarEndereco() {
        return new Endereco(
                java.util.UUID.randomUUID(),
                "Rua A",
                "123",
                "Apto 101",
                "São Paulo",
                "12345-678"
        );
    }

}
