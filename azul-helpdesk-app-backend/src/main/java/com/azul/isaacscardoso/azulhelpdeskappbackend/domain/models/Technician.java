package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Technician extends Person {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(mappedBy = "technician")
    private List<Request> requests = new ArrayList<>();

    public Technician() {
        addPerfil(Perfil.TECHNICIAN);
    }

    public Technician(Long id, String name, String cpf, String email, String password) {
        super(id, name, cpf, email, password);
        addPerfil(Perfil.TECHNICIAN);
    }
}
