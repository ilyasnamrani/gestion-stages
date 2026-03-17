package org.example.entrepriseservice.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreStageResponse {
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
