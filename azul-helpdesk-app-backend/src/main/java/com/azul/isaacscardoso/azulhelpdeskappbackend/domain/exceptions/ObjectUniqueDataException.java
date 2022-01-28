package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions;

import java.io.Serial;

public class ObjectUniqueDataException extends ObjectDataNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectUniqueDataException(String message, String title) {
        super(message, title);
    }

    public ObjectUniqueDataException(String message, Throwable cause, String title) {
        super(message, cause, title);
    }
}
