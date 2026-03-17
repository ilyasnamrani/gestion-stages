package org.example.entrepriseservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseResponseV2 {
    private Long idEntreprise ;
    private String nomEntreprise;
    private String adresseEntreprise;
    private List<String> offreStageList;
    private List<String> encadrantList;
}
