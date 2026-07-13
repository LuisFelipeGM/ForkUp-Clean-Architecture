package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.TipoUsuarioController;
import com.fiap.forkup.clean.arch.infra.web.dto.TipoUsuarioResponse;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioRequest;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.TipoUsuarioVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tipo-usuario")
@RequiredArgsConstructor
public class TipoUsuarioApiController {

    private final TipoUsuarioController tipoUsuarioController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os tipos de usuário", description = "Retorna uma lista de todos os tipos de usuário disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de usuário retornada com sucesso")
    public List<TipoUsuarioResponse> listarTiposUsuario() {
        return tipoUsuarioController.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar tipo de usuário por ID", description = "Retorna os detalhes de um tipo de usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public TipoUsuarioResponse buscarPorId(@PathVariable UUID id) {
        return tipoUsuarioController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo tipo de usuário", description = "Cria um novo tipo de usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito ao criar tipo usuário (registro duplicado)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UUID criar(@Valid @RequestBody TipoUsuarioVO tipoUsuarioVO) {
        TipoUsuarioRequest tipoUsuarioRequest = mapToDto(tipoUsuarioVO);
        return tipoUsuarioController.criar(tipoUsuarioRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar descrição do tipo de usuário", description = "Altera a descrição de um tipo de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descrição do tipo de usuário alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito ao alterar tipo usuário (registro duplicado)", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TipoUsuarioResponse alterarDescricao(@PathVariable UUID id, @Valid @RequestBody TipoUsuarioVO tipoUsuarioVO) {
        TipoUsuarioRequest tipoUsuarioRequest = mapToDto(tipoUsuarioVO);
        return tipoUsuarioController.alterarDescricao(id, tipoUsuarioRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar tipo de usuário", description = "Deleta um tipo de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    public void deletar(@PathVariable UUID id) {
        tipoUsuarioController.deletar(id);
    }

    private TipoUsuarioRequest mapToDto(TipoUsuarioVO tipoUsuarioVO) {
        return new TipoUsuarioRequest(tipoUsuarioVO.getDescricao());
    }

}
