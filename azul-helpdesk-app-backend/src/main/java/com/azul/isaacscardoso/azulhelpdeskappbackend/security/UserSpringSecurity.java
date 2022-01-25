package com.azul.isaacscardoso.azulhelpdeskappbackend.security;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSpringSecurity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    private String cpf;

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity() { }

    public UserSpringSecurity(Long id, String email, String password, String cpf, Set<Perfil> perfils) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.authorities = perfils.stream().map(p -> new SimpleGrantedAuthority(p.getDescription()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
