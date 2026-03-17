package org.example.entrepriseservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
//@ToString(exclude = {"offreStage", "encadrants"})
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntreprise;

    private String nomEntreprise;
    private String adresseEntreprise;

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    private List<OffreStage> offresStage = new ArrayList<>();

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    private List<Encadrant> encadrants = new ArrayList<>();
}

