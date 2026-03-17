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
//@ToString(exclude = {"entreprise", "offresStage"})
public class Encadrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEncadrant;

    private String nomEncadrant;
    private String prenomEncadrant;
    private String telephoneEncadrant;
    private String emailEncadrant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntreprise")
    @JsonIgnore
    private Entreprise entreprise;

    @OneToMany(mappedBy = "encadrant")
    @JsonIgnore
    private List<OffreStage> offresStage = new ArrayList<>();
}
