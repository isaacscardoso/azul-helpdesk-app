package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    OPEN(0, "OPEN"),
    IN_PROGRESS(1, "IN_PROGRESS"),
    CLOSED(2, "HIGH");

    private final Integer code;
    private final String description;

    Status(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Status toEnum(Integer code) {
        if (code == null) {
            return null;
        } else {
            for (Status p : Status.values()) {
                if (code.equals(p.getCode())) {
                    return p;
                }
            }
        }
        throw new IllegalArgumentException("Este Status está inválido!");
    }
}
