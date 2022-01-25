package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums;

import lombok.Getter;

@Getter
public enum Perfil {
    ADMINISTRATOR(0, "ROLE_ADMINISTRATOR"),
    CUSTOMER(1, "ROLE_CUSTOMER"),
    TECHNICIAN(2, "ROLE_TECHNICIAN");

    private final Integer code;
    private final String description;

    Perfil(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Perfil toEnum(Integer code) {
        if (code == null) {
            return null;
        } else {
            for (Perfil p : Perfil.values()) {
                if (code.equals(p.getCode())) {
                    return p;
                }
            }
        }
        throw new IllegalArgumentException("Este Perfil está inválido!");
    }
}
