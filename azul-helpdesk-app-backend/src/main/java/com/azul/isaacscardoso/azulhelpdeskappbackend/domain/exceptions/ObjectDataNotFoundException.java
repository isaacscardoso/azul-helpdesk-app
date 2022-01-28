package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions;

import lombok.Getter;

import java.io.Serial;

public class ObjectDataNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final String title;

    public ObjectDataNotFoundException(String message, String title) {
        super(message);
        this.title = title;
    }

    public ObjectDataNotFoundException(String message, Throwable cause, String title) {
        super(message, cause);
        this.title = title;
    }
}
