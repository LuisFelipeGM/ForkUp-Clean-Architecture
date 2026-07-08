package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.TipoUsuarioController;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioReponse;
import com.fiap.forkup.clean.arch.core.dto.TipoUsuarioRequest;
import com.fiap.forkup.clean.arch.infra.web.vo.TipoUsuarioVO;
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
    public List<TipoUsuarioReponse> listarTiposUsuario() {
        return tipoUsuarioController.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoUsuarioReponse buscarPorId(@PathVariable UUID id) {
        return tipoUsuarioController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@Valid @RequestBody TipoUsuarioVO tipoUsuarioVO) {
        TipoUsuarioRequest tipoUsuarioRequest = mapToDto(tipoUsuarioVO);
        return tipoUsuarioController.criar(tipoUsuarioRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoUsuarioReponse alterarDescricao(@PathVariable UUID id, @Valid @RequestBody TipoUsuarioVO tipoUsuarioVO) {
        TipoUsuarioRequest tipoUsuarioRequest = mapToDto(tipoUsuarioVO);
        return tipoUsuarioController.alterarDescricao(id, tipoUsuarioRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        tipoUsuarioController.deletar(id);
    }

    private TipoUsuarioRequest mapToDto(TipoUsuarioVO tipoUsuarioVO) {
        return new TipoUsuarioRequest(tipoUsuarioVO.getDescricao());
    }

}
