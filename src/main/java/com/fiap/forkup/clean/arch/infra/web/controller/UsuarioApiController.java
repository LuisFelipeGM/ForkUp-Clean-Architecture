package com.fiap.forkup.clean.arch.infra.web.controller;

import com.fiap.forkup.clean.arch.core.controller.UsuarioController;
import com.fiap.forkup.clean.arch.core.dto.*;
import com.fiap.forkup.clean.arch.infra.persistence.mapper.PaginaMapper;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioAlterarTipoVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioCreateVO;
import com.fiap.forkup.clean.arch.infra.web.vo.UsuarioUpdateVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioApiController {

    private final UsuarioController usuarioController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Pagina<UsuarioResponsePartial> listarTodosUsuarios(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return usuarioController.listarTodosPaginado(new PaginacaoRequest(page, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseFull buscarPorId(@PathVariable UUID id) {
        return usuarioController.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID criar(@Valid @RequestBody UsuarioCreateVO usuarioCreateVO) {
        UsuarioRequestCreate usuarioRequestCreate = mapCreateToDto(usuarioCreateVO);
        return usuarioController.criar(usuarioRequestCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseFull alterarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioUpdateVO usuarioUpdateVO) {
        UsuarioRequestUpdate usuarioRequestUpdate = mapUpdateToDto(usuarioUpdateVO);
        return usuarioController.alterar(id, usuarioRequestUpdate);
    }

    @PutMapping("/alterar-tipo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseFull alterarTipoUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioAlterarTipoVO usuarioAlterarTipoVO) {
        return usuarioController.alterarTipoUsuario(id, new AtualizarTipoDoUsuarioRequest(usuarioAlterarTipoVO.getTipoUsuario()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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
