package org.example.entrepriseservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseResponse {
    private Long idEntreprise;
    private String nomEntreprise;
    private String adresseEntreprise;
    private List<Long> offreStageList;
    private List<Long> encadrantList;
}
