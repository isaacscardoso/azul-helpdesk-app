package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCpf(String cpf);
    Optional<Customer> findByEmail(String email);
}
