package org.example.candidatservice;

import org.example.candidatservice.entities.Candidat;
import org.example.candidatservice.enums.Filiere;
import org.example.candidatservice.enums.Niveau;
import org.example.candidatservice.repositories.CandidatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
public class CandidatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandidatServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CandidatRepository candidatRepository) {
        return args -> {

            // Création d'un candidat avec Builder
            Candidat candidat1 = Candidat.builder()
                    .nomCandidat("Nom1")
                    .idEncadrant(1L)
                    .idOffresStagePos(new ArrayList<>(List.of(1L, 2L)))
                    .idOffresStageAccepted(new ArrayList<>(List.of(1L)))
                    .prenomCandidat("Prenom1")
                    .emailCandidat("candidat1@example.com")
                    // .password("password123")
                    .filiere(Filiere.SECURITE)
                    .niveau(Niveau.SENIOR)
                    .competences("Java, Spring Boot, SQL")
                    .adresseCandidat("123 Rue Exemple, Ville")
                    .telephoneCandidat("0612345678")
                    .build();

            Candidat candidat2 = Candidat.builder()
                    .nomCandidat("Nom2")
                    .idEncadrant(2L)
                    .idOffresStagePos(new ArrayList<>(List.of(1L, 2L)))
                    .idOffresStageAccepted(new ArrayList<>(List.of(2L)))
                    .prenomCandidat("Prenom2")
                    .emailCandidat("candidat2@example.com")
                    // .password("password123")
                    .filiere(Filiere.DEV)
                    .niveau(Niveau.JUNIOR)
                    .competences("Python, IoT, Arduino")
                    .adresseCandidat("456 Rue Exemple, Ville")
                    .telephoneCandidat("0698765432")
                    .build();

            // Sauvegarde en base
            candidatRepository.save(candidat1);
            candidatRepository.save(candidat2);

        };
    }
}
