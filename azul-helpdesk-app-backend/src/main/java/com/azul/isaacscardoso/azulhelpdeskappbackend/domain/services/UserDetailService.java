package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Person;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories.PersonRepository;
import com.azul.isaacscardoso.azulhelpdeskappbackend.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private PersonRepository personRepository;

    public UserDetailService() { }

    @Autowired
    public UserDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = this.personRepository.findByEmail(email);
        if (person.isPresent()) {
            return new UserSpringSecurity(
                    person.get().getId(),
                    person.get().getEmail(),
                    person.get().getPassword(),
                    person.get().getCpf(),
                    person.get().getPerfils()
            );
        }
        throw new UsernameNotFoundException(email);
    }
}
