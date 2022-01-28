package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.dto;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class TechnicianDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private Set<Integer> perfils = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime generationDate = LocalDateTime.now();

    public TechnicianDTO() {
        addPerfil(Perfil.TECHNICIAN);
    }

    public TechnicianDTO(Technician technician) {
        this.id = technician.getId();
        this.name = technician.getName();
        this.cpf = technician.getCpf();
        this.email = technician.getEmail();
        this.password = technician.getPassword();
        this.perfils = technician.getPerfils().stream().map(Perfil::getCode).collect(Collectors.toSet());
        this.generationDate = technician.getGenerationDate();
        addPerfil(Perfil.TECHNICIAN);
    }

    public Set<Perfil> getPerfils() {
        return this.perfils.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfils.add(perfil.getCode());
    }
}
