package com.azul.isaacscardoso.azulhelpdeskappbackend.api.controllers;

import com.azul.isaacscardoso.azulhelpdeskappbackend.api.models.TechnicianDTO;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.TechnicianService;
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
@RequestMapping("/tecnicos")
public class TechnicianController {

    private TechnicianService technicianService;

    public TechnicianController() { }

    @Autowired
    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping
    public ResponseEntity<List<TechnicianDTO>> findAll() {
        List<Technician> technicians = this.technicianService.findAll();
        return ResponseEntity.ok(technicians.stream().map(TechnicianDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianDTO> findById(@PathVariable Long id) {
        Optional<Technician> technician = Optional.ofNullable(this.technicianService.findById(id));
        return technician.map(technician_ -> ResponseEntity.ok(new TechnicianDTO(technician_))).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<TechnicianDTO> findByCpf(@PathVariable String cpf) {
        Optional<Technician> technician = Optional.ofNullable(this.technicianService.findByCpf(cpf));
        return technician.map(technician_ -> ResponseEntity.ok(new TechnicianDTO(technician_))).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<TechnicianDTO> findByEmail(@PathVariable String email) {
        Optional<Technician> technician = Optional.ofNullable(this.technicianService.findByEmail(email));
        return technician.map(technician_ -> ResponseEntity.ok(new TechnicianDTO(technician_))).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Technician> insert(@Valid @RequestBody Technician technician) {
        Technician technician_ = this.technicianService.insert(technician);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(technician_.getId())
                .toUri();
        return ResponseEntity.created(uri).body(technician_);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technician> update(@PathVariable Long id, @Valid @RequestBody Technician technician) {
        Technician technician_ = this.technicianService.update(id, technician);
        return technician_ != null ? ResponseEntity.ok(technician_) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.technicianService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
