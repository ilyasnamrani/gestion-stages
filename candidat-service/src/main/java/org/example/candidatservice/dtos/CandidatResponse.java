package org.example.candidatservice.dtos;

import lombok.*;
import org.example.candidatservice.enums.Filiere;
import org.example.candidatservice.enums.Niveau;
import org.example.candidatservice.models.Encadrant;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatResponse{
    private Long idCandidat;
    private String nomCandidat;
    private String prenomCandidat;
    private String emailCandidat;
    private Niveau niveau;
    private Filiere filiere ;
    private List<Long> idOffresStageAccepted ;
    private List<Long> idOffresStagePos ;
    private List<String> titresStagesPos;
    private List<String> titresStagesAccepted;
    private String infosEncadrant;
}