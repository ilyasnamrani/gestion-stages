package org.example.entrepriseservice.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreStageRequest {
    private String titreOffre ;
    private String descriptionOffre ;
    private LocalDate dateAnnonce;
    private Long entreprise;
    private Long encadrant ;
    private List<Long> idCandidats;
}
