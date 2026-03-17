package org.example.entrepriseservice.repositories;

import org.example.entrepriseservice.entities.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncadrantRepository extends JpaRepository<Encadrant,Long> {
}
