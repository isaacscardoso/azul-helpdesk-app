package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Perfil;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Priority;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.enums.Status;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Customer;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Technician;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.PersonRepository;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBService {

    private PersonRepository personRepository;
    private RequestRepository requestRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DBService() { }

    @Autowired
    public DBService(PersonRepository personRepository, RequestRepository requestRepository, BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void instatiateDatabase() {
        if (this.personRepository.findAll().isEmpty()) {
            // Clientes
            Customer customer1 = new Customer(
                    null,
                    "Roger Waters",
                    "852.651.779-14",
                    "rogerwaters@outlook.com",
                    this.passwordEncoder.encode("roger123waters")
            );

            Customer customer2 = new Customer(
                    null,
                    "Claude Monet",
                    "220.961.776-69",
                    "paintingmonet@outlook.com",
                    this.passwordEncoder.encode("impressionism123")
            );

            Customer customer3 = new Customer(
                    null,
                    "Charles Lutwidge Dodgson",
                    "781.062.093-26",
                    "aliceadventuresunderground@outlook.com",
                    this.passwordEncoder.encode("wonderland123")
            );

            Customer customer4 = new Customer(
                    null,
                    "Sidarta Gautama",
                    "436.382.646-02",
                    "budismo@outlook.com",
                    this.passwordEncoder.encode("iluminacao123")
            );

            List<Customer> customers = new ArrayList<>(Arrays.asList(customer1, customer2, customer3, customer4));
            this.personRepository.saveAll(customers);

            // Tecnicos
            Technician technician1 = new Technician(
                    null,
                    "Isaac Cardoso Silva",
                    "102.943.979-65",
                    "azul@outlook.com",
                    this.passwordEncoder.encode("azul123")
            );
            technician1.addPerfil(Perfil.ADMINISTRATOR);

            Technician technician2 = new Technician(
                    null,
                    "Moema Blues",
                    "574.778.312-29",
                    "moema@outlook.com",
                    this.passwordEncoder.encode("blues123"));

            Technician technician3 = new Technician(
                    null,
                    "Edgard Hufelande Santos Lopes",
                    "681.701.023-32",
                    "vendetta@outlook.com",
                    this.passwordEncoder.encode("xVendettA123")
            );

            Technician technician4 = new Technician(
                    null,
                    "Amelia Blues",
                    "054.098.436-14",
                    "amelia@outlook.com",
                    this.passwordEncoder.encode("twins123")
            );

            List<Technician> technicians = new ArrayList<>(Arrays.asList(technician1, technician2, technician3,
                    technician4));
            this.personRepository.saveAll(technicians);

            // Chamados
            if (this.requestRepository.findAll().isEmpty()) {
                Request request1 = new Request(
                        null,
                        Priority.HIGH,
                        Status.IN_PROGRESS,
                        "Theater",
                        "",
                        customer3,
                        technician1
                );

                Request request2 = new Request(
                        null,
                        Priority.MIDDLE,
                        Status.OPEN,
                        "Show",
                        "",
                        customer1,
                        technician2
                );

                Request request3 = new Request(
                        null,
                        Priority.MIDDLE,
                        Status.CLOSED,
                        "Painting",
                        "",
                        customer2,
                        technician4
                );

                Request request4 = new Request(
                        null,
                        Priority.HIGH,
                        Status.CLOSED,
                        "Yoga",
                        "",
                        customer4,
                        technician3
                );
                
                this.requestRepository.saveAll(Arrays.asList(request1, request2, request3, request4));
            }
        }
    }
}
