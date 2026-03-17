package org.example.candidatservice.repositories;

import org.example.candidatservice.entities.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepository extends JpaRepository<Candidat,Long> {
}
