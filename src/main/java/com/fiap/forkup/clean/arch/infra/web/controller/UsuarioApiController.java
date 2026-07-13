package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.UsuarioController;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.infra.web.dto.UsuarioResponseFull;
import com.fiap.forkup.clean.arch.infra.web.dto.UsuarioResponsePartial;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioAlterarTipoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioCreateVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Endpoints para gerenciamento de usuarios")
public class UsuarioApiController {

    private final UsuarioController usuarioController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista usuarios de forma paginada", description = "Retorna uma lista paginada de usuarios")
    @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso")
    public Pagina<UsuarioResponsePartial> listarTodosUsuarios(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return usuarioController.listarTodosPaginado(new PaginacaoRequest(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca usuario por id", description = "Retorna os dados de um usuario especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioResponseFull.class))),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UsuarioResponseFull buscarPorId(@PathVariable UUID id) {
        return usuarioController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo usuario", description = "Cria um novo usuario no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso", content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito ao criar usuario (registro duplicado)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UUID criar(@Valid @RequestBody UsuarioCreateVO usuarioCreateVO) {
        UsuarioRequestCreate usuarioRequestCreate = mapCreateToDto(usuarioCreateVO);
        return usuarioController.criar(usuarioRequestCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar usuario", description = "Altera os dados de um usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioResponseFull.class))),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito ao atualizar usuario (registro duplicado)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UsuarioResponseFull alterarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioUpdateVO usuarioUpdateVO) {
        UsuarioRequestUpdate usuarioRequestUpdate = mapUpdateToDto(usuarioUpdateVO);
        return usuarioController.alterar(id, usuarioRequestUpdate);
    }

    @PutMapping("/alterar-tipo/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar tipo de usuario", description = "Altera o tipo de um usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo do usuario atualizado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioResponseFull.class))),
            @ApiResponse(responseCode = "404", description = "Usuario ou Tipo Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UsuarioResponseFull alterarTipoUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioAlterarTipoVO usuarioAlterarTipoVO) {
        return usuarioController.alterarTipoUsuario(id, new AtualizarTipoDoUsuarioRequest(usuarioAlterarTipoVO.getTipoUsuario()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar usuario", description = "Remove um usuario existente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public void deletar(@PathVariable UUID id) {
        usuarioController.deletar(id);
    }

    private UsuarioRequestCreate mapCreateToDto(UsuarioCreateVO usuarioCreateVO) {
        return new UsuarioRequestCreate(
                usuarioCreateVO.getNome(),
                usuarioCreateVO.getEmail(),
                usuarioCreateVO.getLogin(),
                usuarioCreateVO.getSenha(),
                usuarioCreateVO.getTipoUsuario(),
                new EnderecoRequest(
                        usuarioCreateVO.getEndereco().getLogradouro(),
                        usuarioCreateVO.getEndereco().getNumero(),
                        usuarioCreateVO.getEndereco().getComplemento(),
                        usuarioCreateVO.getEndereco().getCidade(),
                        usuarioCreateVO.getEndereco().getCep()
                ));
    }

    private UsuarioRequestUpdate mapUpdateToDto(UsuarioUpdateVO usuarioUpdateVO) {
        return new UsuarioRequestUpdate(
                usuarioUpdateVO.getNome(),
                usuarioUpdateVO.getEmail(),
                usuarioUpdateVO.getLogin(),
                new EnderecoRequest(
                        usuarioUpdateVO.getEndereco().getLogradouro(),
                        usuarioUpdateVO.getEndereco().getNumero(),
                        usuarioUpdateVO.getEndereco().getComplemento(),
                        usuarioUpdateVO.getEndereco().getCidade(),
                        usuarioUpdateVO.getEndereco().getCep()
                ));
    }

}
