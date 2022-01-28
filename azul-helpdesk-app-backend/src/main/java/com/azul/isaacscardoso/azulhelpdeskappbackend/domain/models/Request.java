package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Priority;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime openingDate = LocalDateTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime closingDate;

    private Priority priority;
    private Status status;
    private String title;
    private String observations;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return getId().equals(request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
