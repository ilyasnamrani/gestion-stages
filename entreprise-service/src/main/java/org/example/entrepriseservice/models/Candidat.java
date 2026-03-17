package org.example.entrepriseservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.entrepriseservice.enums.Filiere;
import org.example.entrepriseservice.enums.Niveau;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidat{
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
