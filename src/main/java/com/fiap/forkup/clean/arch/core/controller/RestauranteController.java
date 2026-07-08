package com.fiap.forkup.clean.arch.core.controller;

import com.fiap.forkup.clean.arch.core.domain.Endereco;
import com.fiap.forkup.clean.arch.core.domain.ItemCardapio;
import com.fiap.forkup.clean.arch.core.domain.Restaurante;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.core.mapper.EnderecoMapper;
import com.fiap.forkup.clean.arch.core.mapper.ItemCardapioMapper;
import com.fiap.forkup.clean.arch.core.mapper.RestauranteMapper;
import com.fiap.forkup.clean.arch.core.usecase.restaurante.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RestauranteController {

    private final ListarRestauranteUseCase listarRestauranteUseCase;

    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    private final CriarRestauranteUseCase criarRestauranteUseCase;

    private final AlterarRestauranteUseCase alterarRestauranteUseCase;

    private final AlterarDonoRestauranteUseCase alterarDonoRestauranteUseCase;

    private final DeletarRestauranteUseCase deletarRestauranteUseCase;

    private final ListarCardapioRestauranteUseCase listarCardapioRestauranteUseCase;

    private final AdicionarItemCardapioUseCase adicionarItemCardapioUseCase;

    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    private final RemoverItemCardapioUseCase removerItemCardapioUseCase;

    private final RestauranteMapper restauranteMapper;

    private final ItemCardapioMapper itemCardapioMapper;

    private final EnderecoMapper enderecoMapper;

    public Pagina<RestauranteResponsePartial> listarTodosPaginado(PaginacaoRequest paginacaoRequest) {
        Pagina<Restaurante> restaurantePagina = listarRestauranteUseCase.execute(paginacaoRequest.pagina(), paginacaoRequest.tamanho());
        return restaurantePagina.map(restauranteMapper::domainToDtoPartial);
    }

    public RestauranteResponseFull buscarPorId(final UUID id) {
        Restaurante restaurante = buscarRestaurantePorIdUseCase.execute(id);
        return restauranteMapper.domainToDtoFull(restaurante);
    }

    public UUID criar(final RestauranteRequestCreate restauranteRequestCreate) {
        Restaurante restaurante = restauranteMapper.requestToDomain(restauranteRequestCreate);
        return criarRestauranteUseCase.execute(restaurante);
    }

    public RestauranteResponseFull alterar(final UUID id, final RestauranteRequestUpdate restauranteRequestUpdate) {
        Endereco endereco = enderecoMapper.toDomain(restauranteRequestUpdate.endereco());
        AtualizarRestauranteInput input = new AtualizarRestauranteInput(
                restauranteRequestUpdate.nome(),
                restauranteRequestUpdate.tipoCozinha(),
                restauranteRequestUpdate.horarioFuncionamento(),
                endereco
        );
        Restaurante restauranteAtualizado = alterarRestauranteUseCase.execute(id, input);
        return restauranteMapper.domainToDtoFull(restauranteAtualizado);
    }

    public RestauranteResponseFull alterarDonoRestaurante(UUID idRestaurante, AtualizarDonoDoRestauranteRequest request) {
        Restaurante restaurante = alterarDonoRestauranteUseCase.execute(idRestaurante, request.idDono());
        return restauranteMapper.domainToDtoFull(restaurante);
    }

    public void deletar(final UUID id) {
        deletarRestauranteUseCase.execute(id);
    }

    public List<ItemCardapioResponse> listarCardapio(UUID idRestaurante) {
        List<ItemCardapio> cardapio = listarCardapioRestauranteUseCase.execute(idRestaurante);
        return cardapio.stream()
                .map(itemCardapioMapper::domainToDto)
                .toList();
    }

    public ItemCardapioResponse adicionarItemAoCardapio(UUID idRestaurante, ItemCardapioRequest itemCardapioRequest) {
        ItemCardapio itemCardapio = itemCardapioMapper.requestToDomain(itemCardapioRequest);
        ItemCardapio itemAdicionado = adicionarItemCardapioUseCase.execute(idRestaurante, itemCardapio);
        return itemCardapioMapper.domainToDto(itemAdicionado);
    }

    public ItemCardapioResponse atualizarItemDoCardapio(UUID idRestaurante, UUID itemId, ItemCardapioRequest itemCardapioRequest) {
        ItemCardapio itemCardapio = itemCardapioMapper.requestToDomain(itemCardapioRequest);
        ItemCardapio itemAtualizado = atualizarItemCardapioUseCase.execute(idRestaurante, itemId, itemCardapio);
        return itemCardapioMapper.domainToDto(itemAtualizado);
    }

    public void removerItemCardapio(UUID idRestaurante, UUID idItemCardapio) {
        removerItemCardapioUseCase.execute(idRestaurante, idItemCardapio);
    }

}
