package org.example.candidatservice.dtos;

import lombok.*;
import org.example.candidatservice.enums.Filiere;
import org.example.candidatservice.enums.Niveau;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatRequest {
    private String nomCandidat;
    private String prenomCandidat;
    private String emailCandidat;
    private String adresseCandidat;
    private String telephoneCandidat;
    private Long idEncadrant;
    private List<Long> idOffresStageAccepted ;
    private List<Long> idOffresStagePos ;
    private Filiere filiere ;
    private String Competences ;
    private Niveau niveau;
//  private String password ;
}