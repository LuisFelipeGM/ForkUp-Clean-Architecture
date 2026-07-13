package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.RestauranteController;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.infra.web.dto.ItemCardapioResponse;
import com.fiap.forkup.clean.arch.infra.web.dto.RestauranteResponseFull;
import com.fiap.forkup.clean.arch.infra.web.dto.RestauranteResponsePartial;
import com.fiap.forkup.clean.arch.infra.web.exceptionhandler.ErrorResponse;
import com.fiap.forkup.clean.arch.infra.web.vo.ItemCardapioVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteAlterarDonoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteCreateVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteUpdateVO;
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
@RequestMapping("/api/restaurante")
@RequiredArgsConstructor
public class RestauranteApiController {

    private final RestauranteController restauranteController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista restaurantes de forma paginada", description = "Retorna uma lista paginada de restaurantes")
    @ApiResponse(responseCode = "200", description = "Lista paginada retornada com sucesso")
    public Pagina<RestauranteResponsePartial> listarTodosRestaurantes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
         return restauranteController.listarTodosPaginado(new PaginacaoRequest(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca restaurante por id", description = "Retorna os dados de um restaurante especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public RestauranteResponseFull buscarPorId(@PathVariable UUID id) {
        return restauranteController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo restaurante", description = "Cria um novo restaurante no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UUID criar(@Valid @RequestBody RestauranteCreateVO restauranteCreateVO) {
        RestauranteRequestCreate restauranteRequestCreate = mapCreateToDto(restauranteCreateVO);
        return restauranteController.criar(restauranteRequestCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar restaurante", description = "Altera os dados de um restaurante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public RestauranteResponseFull alterarRestaurante(@PathVariable UUID id, @Valid @RequestBody RestauranteUpdateVO restauranteUpdateVO) {
        RestauranteRequestUpdate restauranteRequestUpdate = mapUpdateToDto(restauranteUpdateVO);
        return restauranteController.alterar(id, restauranteRequestUpdate);
    }

    @PutMapping("/alterar-dono/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Alterar dono do restaurante", description = "Altera o dono de um restaurante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dono do restaurante alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante ou dono não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public RestauranteResponseFull alterarDonoRestaurante(@PathVariable UUID id, @Valid @RequestBody RestauranteAlterarDonoVO restauranteAlterarDonoVO) {
        return restauranteController.alterarDonoRestaurante(id, new AtualizarDonoDoRestauranteRequest(restauranteAlterarDonoVO.getIdDono()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar restaurante", description = "Deleta um restaurante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public void deletar(@PathVariable UUID id) {
        restauranteController.deletar(id);
    }

    @GetMapping("/{idRestaurante}/cardapio")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar cardápio do restaurante", description = "Retorna uma lista de itens do cardápio de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cardápio listado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public List<ItemCardapioResponse> listarCardapio(@PathVariable UUID idRestaurante) {
        return restauranteController.listarCardapio(idRestaurante);
    }

    @PostMapping("/{idRestaurante}/cardapio")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar item ao cardápio", description = "Adiciona um novo item ao cardápio de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item adicionado ao cardápio com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ItemCardapioResponse adicionarItemAoCardapio(@PathVariable UUID idRestaurante, @Valid @RequestBody ItemCardapioVO itemCardapioVO) {
        ItemCardapioRequest itemCardapioRequest = mapItemCardapioToDto(itemCardapioVO);
        return restauranteController.adicionarItemAoCardapio(idRestaurante, itemCardapioRequest);
    }

    @PutMapping("/{idRestaurante}/cardapio/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar item do cardápio", description = "Atualiza um item existente no cardápio de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item do cardápio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item do cardápio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ItemCardapioResponse atualizarItemDoCardapio(@PathVariable UUID idRestaurante, @PathVariable UUID itemId, @Valid @RequestBody ItemCardapioVO itemCardapioVO) {
        ItemCardapioRequest itemCardapioRequest = mapItemCardapioToDto(itemCardapioVO);
        return restauranteController.atualizarItemDoCardapio(idRestaurante, itemId, itemCardapioRequest);
    }

    @DeleteMapping("/{idRestaurante}/cardapio/{idItemCardapio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover item do cardápio", description = "Remove um item existente do cardápio de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item do cardápio removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item do cardápio não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public void removerItemCardapio(@PathVariable UUID idRestaurante, @PathVariable UUID idItemCardapio) {
        restauranteController.removerItemCardapio(idRestaurante, idItemCardapio);
    }

    private RestauranteRequestCreate mapCreateToDto(RestauranteCreateVO restauranteCreateVO) {
        return new RestauranteRequestCreate(
                restauranteCreateVO.getNome(),
                restauranteCreateVO.getTipoCozinha(),
                restauranteCreateVO.getHorarioFuncionamento(),
                restauranteCreateVO.getDonoId(),
                new EnderecoRequest(
                        restauranteCreateVO.getEndereco().getLogradouro(),
                        restauranteCreateVO.getEndereco().getNumero(),
                        restauranteCreateVO.getEndereco().getComplemento(),
                        restauranteCreateVO.getEndereco().getCidade(),
                        restauranteCreateVO.getEndereco().getCep()
                ));
    }

    private RestauranteRequestUpdate mapUpdateToDto(RestauranteUpdateVO restauranteUpdateVO) {
        return new RestauranteRequestUpdate(
                restauranteUpdateVO.getNome(),
                restauranteUpdateVO.getTipoCozinha(),
                restauranteUpdateVO.getHorarioFuncionamento(),
                new EnderecoRequest(
                        restauranteUpdateVO.getEndereco().getLogradouro(),
                        restauranteUpdateVO.getEndereco().getNumero(),
                        restauranteUpdateVO.getEndereco().getComplemento(),
                        restauranteUpdateVO.getEndereco().getCidade(),
                        restauranteUpdateVO.getEndereco().getCep()
                ));
    }

    private ItemCardapioRequest mapItemCardapioToDto(ItemCardapioVO itemCardapioVO) {
        return new ItemCardapioRequest(
                itemCardapioVO.getNome(),
                itemCardapioVO.getDescricao(),
                itemCardapioVO.getPreco(),
                itemCardapioVO.getApenasRestaurante(),
                itemCardapioVO.getPathFoto()
        );
    }

}
