package org.example.entrepriseservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseResponseV2 implements Serializable {
    private Long idEntreprise ;
    private String nomEntreprise;
    private String adresseEntreprise;
    private List<String> offreStageList;
    private List<String> encadrantList;
}
