package org.example.entrepriseservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseRequest {

        private String nomEntreprise;
        private String adresseEntreprise;
        private List<Long> offresStage;
        private List<Long> encadrants;
    }

