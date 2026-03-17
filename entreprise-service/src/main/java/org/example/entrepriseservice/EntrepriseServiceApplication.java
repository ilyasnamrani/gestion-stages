package org.example.entrepriseservice;

import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;



@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
public class EntrepriseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntrepriseServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(EntrepriseRepository entrepriseRepository,
                             EncadrantRepository encadrantRepository,
                             OffreStageRepository offreStageRepository) {
        return args -> {

            // ===== 3 ENTREPRISES =====
            Entreprise e1 = Entreprise.builder()
                    .nomEntreprise("TechCorp")
                    .adresseEntreprise("Casablanca")
                    .build();

            Entreprise e2 = Entreprise.builder()
                    .nomEntreprise("DataSoft")
                    .adresseEntreprise("Rabat")
                    .build();

            Entreprise e3 = Entreprise.builder()
                    .nomEntreprise("CloudInnov")
                    .adresseEntreprise("Tanger")
                    .build();

            // ===== 3 ENCADRANTS =====
            Encadrant c1 = Encadrant.builder()
                    .nomEncadrant("Karim")
                    .prenomEncadrant("Ben Ali")
                    .telephoneEncadrant("0600000001")
                    .emailEncadrant("karim@techcorp.com")
                    .entreprise(e1)
                    .build();

            Encadrant c2 = Encadrant.builder()
                    .nomEncadrant("Salma")
                    .prenomEncadrant("El Amrani")
                    .telephoneEncadrant("0600000002")
                    .emailEncadrant("salma@datasoft.com")
                    .entreprise(e2)
                    .build();

            Encadrant c3 = Encadrant.builder()
                    .nomEncadrant("Youssef")
                    .prenomEncadrant("Alaoui")
                    .telephoneEncadrant("0600000003")
                    .emailEncadrant("youssef@cloudinnov.com")
                    .entreprise(e3)
                    .build();

            // ===== 3 OFFRES DE STAGE =====
            OffreStage o1 = OffreStage.builder()
                    .titreOffre("Stage Java Spring")
                    .descriptionOffre("Microservices & Spring Boot")
                    .entreprise(e1)
                    .encadrant(c1)
                    .dateAnnonce(LocalDate.of(2026, 2, 4))
                    .nombrePostules(2L)
                    .idCandidats(List.of(1L, 2L, 3L))
                    .build();

            OffreStage o2 = OffreStage.builder()
                    .titreOffre("Stage Data Science")
                    .descriptionOffre("Python, ML, Pandas")
                    .entreprise(e2)
                    .encadrant(c2)
                    .dateAnnonce(LocalDate.of(2025, 8, 5))
                    .nombrePostules(4L)
                    .idCandidats(List.of(1L, 2L))
                    .build();

            OffreStage o3 = OffreStage.builder()
                    .titreOffre("Stage Cloud DevOps")
                    .descriptionOffre("Docker, CI/CD, AWS")
                    .entreprise(e3)
                    .encadrant(c3)
                    .dateAnnonce(LocalDate.of(2025, 6, 8))
                    .nombrePostules(1L)
                    .idCandidats(List.of(2L, 3L))
                    .build();

            entrepriseRepository.saveAll(List.of(e1, e2, e3));
            encadrantRepository.saveAll(List.of(c1, c2, c3));
            offreStageRepository.saveAll(List.of(o1, o2, o3));


        };
    }
}
