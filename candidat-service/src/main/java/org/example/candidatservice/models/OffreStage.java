package org.example.candidatservice.models;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreStage implements Serializable {
    private Long idOffreStage;
    private String titreOffre ;
    private String descriptionOffre ;
    private String nomEntreprise ;
    private String nomEncadrant ;
    private LocalDate dateAnnonce;
    private String prenomEncadrant ;
    private List<String> nomCandidats ;
    private Long nombrePostules ;
}

