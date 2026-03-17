package org.example.candidatservice.entities;

import org.example.candidatservice.enums.Filiere;
import org.example.candidatservice.enums.Niveau;
import org.example.candidatservice.models.Encadrant;
import org.example.candidatservice.models.OffreStage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidat;

    @NotBlank(message = "Le nom  est obligatoire")
    private String nomCandidat;

    @NotBlank(message = "Le nom  est obligatoire")
    private String prenomCandidat;

    private String adresseCandidat;

    private Long idEncadrant;

    private List<Long> idOffresStagePos = new ArrayList<>();

    private List<Long> idOffresStageAccepted = new ArrayList<>();

    @Transient
    private List<OffreStage> offresStagePos = new ArrayList<>();

    @Transient
    private List<OffreStage> offresStageAccepted = new ArrayList<>();

    @Transient
    private Encadrant encardant;

    @Size(min = 10, max = 10, message = "Le numéro doit contenir 10 chiffres")
    private String telephoneCandidat;

    @Email(message = "Email est obligatoire")
    private String emailCandidat;

    @Enumerated(EnumType.STRING)
    private Filiere filiere;

    // @Value("false")
    // private Boolean commence;

    @Size(max = 300)
    private String competences;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    // @OneToMany(mappedBy = "candidat")
    // private List<Message> messages;

    // @PrePersist
    // @PreUpdate
    // public void hashPassword() {
    // if (password != null ) {
    // this.password = new BCryptPasswordEncoder().encode(password);
    // }
    // }
    // @NotBlank
    // private String password;
}
