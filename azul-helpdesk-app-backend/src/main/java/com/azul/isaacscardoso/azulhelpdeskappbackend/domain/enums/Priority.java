package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW(0, "LOW"),
    MIDDLE(1, "MIDDLE"),
    HIGH(2, "HIGH");

    private final Integer code;
    private final String description;

    Priority(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Priority toEnum(Integer code) {
        if (code == null) {
            return null;
        } else {
            for (Priority p : Priority.values()) {
                if (code.equals(p.getCode())) {
                    return p;
                }
            }
        }
        throw new IllegalArgumentException("Esta Prioridade está inválida!");
    }
}
