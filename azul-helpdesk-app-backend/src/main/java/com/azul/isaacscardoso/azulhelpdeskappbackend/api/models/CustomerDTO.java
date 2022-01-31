package com.azul.isaacscardoso.azulhelpdeskappbackend.api.models;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
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
public class CustomerDTO implements Serializable {

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

    public CustomerDTO() {
        addPerfil(Perfil.CUSTOMER);
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.cpf = customer.getCpf();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.perfils = customer.getPerfils().stream().map(Perfil::getCode).collect(Collectors.toSet());
        this.generationDate = customer.getGenerationDate();
        addPerfil(Perfil.CUSTOMER);
    }

    public Set<Perfil> getPerfils() {
        return this.perfils.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfils.add(perfil.getCode());
    }
}
