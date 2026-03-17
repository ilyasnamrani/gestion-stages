package org.example.entrepriseservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.entrepriseservice.models.Candidat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
//@ToString(exclude = {"entreprise", "encadrant"})
public class OffreStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreStage;

    private String titreOffre;
    private String descriptionOffre;

    private LocalDate dateAnnonce ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntreprise")
    @JsonIgnore
    private Entreprise entreprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encadrant_id")
    @JsonIgnore
    private Encadrant encadrant;

    private List<Long> idCandidats = new ArrayList<>() ;
    @Transient
    private List<Candidat> candidats = new ArrayList<>();

    private Long nombrePostules;
}
