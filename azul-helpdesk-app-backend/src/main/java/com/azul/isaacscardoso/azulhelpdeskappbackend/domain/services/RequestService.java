package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.exceptions.ObjectDataNotFoundException;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private RequestRepository requestRepository;
    private CustomerService customerService;
    private TechnicianService technicianService;
    private final String OBJ_NOT_FOUND_MESSAGE = "O Chamado não foi encontrado!";
    private final String EX_TITLE_MESSAGE = "Dados não encontrados.";

    public RequestService() { }

    @Autowired
    public RequestService(RequestRepository requestRepository, CustomerService customerService,
                          TechnicianService technicianService) {
        this.requestRepository = requestRepository;
        this.customerService = customerService;
        this.technicianService = technicianService;
    }

    public List<Request> findAll() {
        return this.requestRepository.findAll();
    }

    public Request findById(Long id) {
        Optional<Request> request = this.requestRepository.findById(id);
        return request.orElseThrow(() -> new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE));
    }

    @Transactional
    public Request insert(Request request) {
        return this.requestRepository.save(instantiateRequest(request));
    }

    @Transactional
    public Request update(Long id, Request request) {
        if (!this.requestRepository.existsById(id))
            throw new ObjectDataNotFoundException(OBJ_NOT_FOUND_MESSAGE, EX_TITLE_MESSAGE);
        request.setId(id);
        return this.requestRepository.save(request);
    }

    public Request instantiateRequest(Request request) {
        Customer customer = this.customerService.findById(request.getCustomer().getId());
        Technician technician = this.technicianService.findById(request.getTechnician().getId());
        Request request_ = new Request();

        if (request.getId() != null)
            request_.setId(request.getId());

        if (request.getStatus().getCode().equals(2))
            request_.setClosingDate(LocalDateTime.now());

        request_.setCustomer(customer);
        request_.setTechnician(technician);
        request_.setPriority(request.getPriority());
        request_.setStatus(request.getStatus());
        request_.setTitle(request.getTitle());
        request_.setObservations(request.getObservations());
        return request_;
    }
}
