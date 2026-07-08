package com.fiap.forkup.clean.arch.infra.persistence.mapper;

import com.fiap.forkup.clean.arch.core.dto.Pagina;
import org.springframework.data.domain.Page;

public class PaginaMapper {

    private PaginaMapper() {
    }

    public static <T>Pagina<T> from(Page<T> page) {
        return new Pagina<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
