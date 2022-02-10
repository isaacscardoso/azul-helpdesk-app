package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public abstract class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank
    @Size(min = 2, max = 60)
    @Column(length = 60)
    protected String name;

    @NotBlank
    @CPF
    @Size(min = 11, max = 14)
    @Column(length = 14, unique = true)
    protected String cpf;

    @NotBlank
    @Size(min = 10)
    @Column(unique = true)
    protected String email;

    @NotBlank
    @Size(min = 8, max = 60)
    @Column(length = 60)
    protected String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    protected Set<Integer> perfils = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime generationDate = LocalDateTime.now();

    public Person() { }

    public Person(Long id, String name, String cpf, String email, String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    public Set<Perfil> getPerfils() {
        return this.perfils.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfils.add(perfil.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId().equals(person.getId()) && getCpf().equals(person.getCpf()) && getEmail().equals(person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCpf(), getEmail());
    }
}
