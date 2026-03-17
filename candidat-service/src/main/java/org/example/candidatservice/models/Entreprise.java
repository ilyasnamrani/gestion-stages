package org.example.candidatservice.models;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise{
    private Long idEntreprise;
    private String nomEntreprise;
    private String adresseEntreprise;
    private List<String> offreStageList;
    private List<String> encadrantList;
}
