package com.azul.isaacscardoso.azulhelpdeskappbackend.api.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class APIError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String title;
    private String message;
    private String path;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateHour;

    public APIError() { }

    public APIError(Integer status, String title, String message, String path, LocalDateTime dateHour) {
        this.status = status;
        this.title = title;
        this.message = message;
        this.path = path;
        this.dateHour = dateHour;
    }
}
