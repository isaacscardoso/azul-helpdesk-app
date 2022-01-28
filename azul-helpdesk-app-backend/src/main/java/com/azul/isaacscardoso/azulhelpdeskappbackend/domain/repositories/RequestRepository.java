package com.azul.isaacscardoso.azulhelpdeskappbackend.domain.repositories;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> { }
