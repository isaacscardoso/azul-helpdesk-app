package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.dto;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime openingDate = LocalDateTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime closingDate;

    private Integer priority;
    private Integer status;
    private String title;
    private String observations;
    private Long technician;
    private Long customer;
    private String customerName;
    private String technicianName;

    public RequestDTO() { }

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.openingDate = request.getOpeningDate();
        this.closingDate = request.getClosingDate();
        this.priority = request.getPriority().getCode();
        this.status = request.getStatus().getCode();
        this.title = request.getTitle();
        this.observations = request.getObservations();
        this.customer = request.getCustomer().getId();
        this.technician = request.getTechnician().getId();
        this.customerName = request.getCustomer().getName();
        this.technicianName = request.getTechnician().getName();
    }
}
