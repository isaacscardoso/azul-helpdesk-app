package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectDataNotFoundException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectUniqueDataException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicianService {

    private TechnicianRepository technicianRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final String OBJ_NOT_FOUND_MESSAGE = "O Técnico não foi encontrado!";
    private final String EX_TITLE_MESSAGE = "Dados não encontrados.";

    public TechnicianService() { }

    public TechnicianService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public boolean cpfUsed(Technician technician) {
        return this.technicianRepository.findByCpf(technician.getCpf()).stream().anyMatch(cpf -> !cpf.equals(technician));
    }

    public boolean emailUsed(Technician technician) {
        return this.technicianRepository.findByCpf(technician.getEmail()).stream().anyMatch(em -> !em.equals(technician));
    }

    public List<Technician> findAll() {
        return this.technicianRepository.findAll();
    }

    public Technician findById(Long id) {
        Optional<Technician> technician = this.technicianRepository.findById(id);
        return technician.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    public Technician findByCpf(String cpf) {
        Optional<Technician> technician = this.technicianRepository.findByCpf(cpf);
        return technician.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    public Technician findByEmail(String email) {
        Optional<Technician> technician = this.technicianRepository.findByEmail(email);
        return technician.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    @Transactional
    public Technician insert(Technician technician) {
        if (this.cpfUsed(technician))
            throw new ObjectUniqueDataException("Este CPF já está cadastrado!", "CPF em uso");
        if (this.emailUsed(technician))
            throw new ObjectUniqueDataException("Este E-MAIL já está cadastrado!", "E-MAIL em uso");
        technician.setPassword(this.passwordEncoder.encode(technician.getPassword()));
        return this.technicianRepository.save(technician);
    }

    @Transactional
    public Technician update(Long id, Technician technician) {
        if (!this.technicianRepository.existsById(id))
            throw new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE);
        technician.setId(id);
        technician.setPassword(this.passwordEncoder.encode(technician.getPassword()));
        return this.technicianRepository.save(technician);
    }

    @Transactional
    public void delete(Long id) {
        if (!this.technicianRepository.existsById(id))
            throw new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE);
        if (this.findById(id).getRequests().size() > 0)
            throw new DataIntegrityViolationException("Este técnico possui ordens de serviço e não pode ser deletado!");
        this.technicianRepository.deleteById(id);
    }
}
