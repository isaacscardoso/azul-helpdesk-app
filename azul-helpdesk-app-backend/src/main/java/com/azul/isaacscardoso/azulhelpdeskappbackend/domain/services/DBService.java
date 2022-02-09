package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Priority;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Status;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.CustomerRepository;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.RequestRepository;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    private CustomerRepository customerRepository;
    private TechnicianRepository technicianRepository;
    private RequestRepository requestRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DBService() { }

    @Autowired
    public DBService(CustomerRepository customerRepository, TechnicianRepository technicianRepository,
                     RequestRepository requestRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.technicianRepository = technicianRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void instatiateDatabase() {
        if (this.technicianRepository.findAll().isEmpty() && this.customerRepository.findAll().isEmpty()) {
            // Técnicos
            Technician tec1 = new Technician(null, "Valdir Cezar", "550.482.150-95", "valdir@mail.com",
                    passwordEncoder.encode("123"));

            tec1.addPerfil(Perfil.ADMINISTRATOR);
            Technician tec2 = new Technician(null, "Richard Stallman", "903.347.070-56",
                    "stallman@mail.com", passwordEncoder.encode("123"));

            Technician tec3 = new Technician(null, "Claude Elwood Shannon", "271.068.470-54",
                    "shannon@mail.com", passwordEncoder.encode("123"));

            Technician tec4 = new Technician(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com",
                    passwordEncoder.encode("123"));

            Technician tec5 = new Technician(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com",
                    passwordEncoder.encode("123"));

            this.technicianRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5));

            // Clientes
            Customer cli1 = new Customer(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com",
                    passwordEncoder.encode("123"));

            Customer cli2 = new Customer(null, "Marie Curie", "322.429.140-06", "curie@mail.com",
                    passwordEncoder.encode("123"));

            Customer cli3 = new Customer(null, "Charles Darwin", "792.043.830-62", "darwin@mail.com",
                    passwordEncoder.encode("123"));

            Customer cli4 = new Customer(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com",
                    passwordEncoder.encode("123"));

            Customer cli5 = new Customer(null, "Max Planck", "081.399.300-83", "planck@mail.com",
                    passwordEncoder.encode("123"));

            this.customerRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4, cli5));

            // Chamados
            if (this.requestRepository.findAll().isEmpty()) {
                Request c1 = new Request(null, Priority.MIDDLE, Status.IN_PROGRESS, "Request 1",
                        "Teste chamado 1", cli1, tec1);

                Request c2 = new Request(null, Priority.HIGH, Status.OPEN, "Request 2", "Teste chamado 2",
                        cli2, tec1);

                Request c3 = new Request(null, Priority.LOW, Status.CLOSED, "Request 3", "Teste chamado 3",
                        cli3, tec2);

                Request c4 = new Request(null, Priority.HIGH, Status.OPEN, "Request 4", "Teste chamado 4",
                        cli3, tec3);

                Request c5 = new Request(null, Priority.MIDDLE, Status.IN_PROGRESS, "Request 5",
                        "Teste chamado 5", cli1, tec2);

                Request c6 = new Request(null, Priority.LOW, Status.CLOSED, "Request 7", "Teste chamado 6",
                        cli5, tec1);

                this.requestRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
            }
        }
    }
}
