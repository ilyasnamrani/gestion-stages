package org.example.entrepriseservice.repositories;

import org.example.entrepriseservice.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}
