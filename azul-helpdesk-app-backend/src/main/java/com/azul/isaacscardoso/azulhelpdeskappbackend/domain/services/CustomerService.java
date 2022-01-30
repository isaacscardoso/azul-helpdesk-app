package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectDataNotFoundException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectUniqueDataException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final String OBJ_NOT_FOUND_MESSAGE = "O Cliente não foi encontrado!";
    private final String EX_TITLE_MESSAGE = "Dados não encontrados.";

    public CustomerService() { }

    public CustomerService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean cpfUsed(Customer customer) {
        return this.customerRepository.findByCpf(customer.getCpf()).stream().anyMatch(cpf -> !cpf.equals(customer));
    }

    public boolean emailUsed(Customer customer) {
        return this.customerRepository.findByCpf(customer.getEmail()).stream().anyMatch(em -> !em.equals(customer));
    }

    public List<Customer> findAll() {
        return this.customerRepository.findAll();
    }

    public Customer findById(Long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        return customer.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    public Customer findByCpf(String cpf) {
        Optional<Customer> customer = this.customerRepository.findByCpf(cpf);
        return customer.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    public Customer findByEmail(String email) {
        Optional<Customer> customer = this.customerRepository.findByEmail(email);
        return customer.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    @Transactional
    public Customer insert(Customer customer) {
        if (this.cpfUsed(customer))
            throw new ObjectUniqueDataException("Este CPF já está cadastrado!", "CPF em uso");
        if (this.emailUsed(customer))
            throw new ObjectUniqueDataException("Este E-MAIL já está cadastrado!", "E-MAIL em uso");
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        return this.customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Long id, Customer customer) {
        if (!this.customerRepository.existsById(id))
            throw new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE);
        customer.setId(id);
        customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
        return this.customerRepository.save(customer);
    }

    @Transactional
    public void delete(Long id) {
        if (!this.customerRepository.existsById(id))
            throw new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE);
        if (this.findById(id).getRequests().size() > 0)
            throw new DataIntegrityViolationException("Este cliente possui ordens de serviço e não pode ser deletado!");
        this.customerRepository.deleteById(id);
    }
}
