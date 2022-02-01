package com.azul.isaacscardoso.azulhelpdeskappbackend.api.controllers;

import com.azul.isaacscardoso.azulhelpdeskappbackend.api.models.CustomerDTO;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController() { }

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<Customer> customers = this.customerService.findAll();
        return ResponseEntity.ok(customers.stream().map(CustomerDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        Optional<Customer> customer = Optional.ofNullable(this.customerService.findById(id));
        return customer.map(customer_ -> ResponseEntity.ok(new CustomerDTO(customer_))).orElseGet(() -> ResponseEntity
                .notFound().build());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> findByCpf(@PathVariable String cpf) {
        Optional<Customer> customer = Optional.ofNullable(this.customerService.findByCpf(cpf));
        return customer.map(customer_ -> ResponseEntity.ok(new CustomerDTO(customer_))).orElseGet(() -> ResponseEntity
                .notFound().build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerDTO> findByEmail(@PathVariable String email) {
        Optional<Customer> customer = Optional.ofNullable(this.customerService.findByEmail(email));
        return customer.map(customer_ -> ResponseEntity.ok(new CustomerDTO(customer_))).orElseGet(() -> ResponseEntity
                .notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> insert(@Valid @RequestBody Customer customer) {
        Customer customer_ = this.customerService.insert(customer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer_.getId())
                .toUri();
        return ResponseEntity.created(uri).body(customer_);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        Customer customer_ = this.customerService.update(id, customer);
        return customer_ != null ? ResponseEntity.ok(customer_) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
