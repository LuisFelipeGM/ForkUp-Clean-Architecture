package com.fiap.forkup.clean.arch.infra.web.exceptionhandler;

import java.time.LocalDateTime;

public record ErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) { }
