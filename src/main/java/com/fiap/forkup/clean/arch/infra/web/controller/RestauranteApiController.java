package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.RestauranteController;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.PaginaMapper;
import com.fiap.forkup.clean.arch.infra.web.vo.ItemCardapioVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteAlterarDonoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteCreateVO;
import com.fiap.forkup.clean.arch.infra.web.vo.RestauranteUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Pagina<RestauranteResponsePartial> listarTodosRestaurantes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
         return restauranteController.listarTodosPaginado(new PaginacaoRequest(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteResponseFull buscarPorId(@PathVariable UUID id) {
        return restauranteController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@Valid @RequestBody RestauranteCreateVO restauranteCreateVO) {
        RestauranteRequestCreate restauranteRequestCreate = mapCreateToDto(restauranteCreateVO);
        return restauranteController.criar(restauranteRequestCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteResponseFull alterarRestaurante(@PathVariable UUID id, @Valid @RequestBody RestauranteUpdateVO restauranteUpdateVO) {
        RestauranteRequestUpdate restauranteRequestUpdate = mapUpdateToDto(restauranteUpdateVO);
        return restauranteController.alterar(id, restauranteRequestUpdate);
    }

    @PutMapping("/alterar-dono/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestauranteResponseFull alterarDonoRestaurante(@PathVariable UUID id, @Valid @RequestBody RestauranteAlterarDonoVO restauranteAlterarDonoVO) {
        return restauranteController.alterarDonoRestaurante(id, new AtualizarDonoDoRestauranteRequest(restauranteAlterarDonoVO.getIdDono()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        restauranteController.deletar(id);
    }

    @GetMapping("/{idRestaurante}/cardapio")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemCardapioResponse> listarCardapio(@PathVariable UUID idRestaurante) {
        return restauranteController.listarCardapio(idRestaurante);
    }

    @PostMapping("/{idRestaurante}/cardapio")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCardapioResponse adicionarItemAoCardapio(@PathVariable UUID idRestaurante, @Valid @RequestBody ItemCardapioVO itemCardapioVO) {
        ItemCardapioRequest itemCardapioRequest = mapItemCardapioToDto(itemCardapioVO);
        return restauranteController.adicionarItemAoCardapio(idRestaurante, itemCardapioRequest);
    }

    @PutMapping("/{idRestaurante}/cardapio/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemCardapioResponse atualizarItemDoCardapio(@PathVariable UUID idRestaurante, @PathVariable UUID itemId, @Valid @RequestBody ItemCardapioVO itemCardapioVO) {
        ItemCardapioRequest itemCardapioRequest = mapItemCardapioToDto(itemCardapioVO);
        return restauranteController.atualizarItemDoCardapio(idRestaurante, itemId, itemCardapioRequest);
    }

    @DeleteMapping("/{idRestaurante}/cardapio/{idItemCardapio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
