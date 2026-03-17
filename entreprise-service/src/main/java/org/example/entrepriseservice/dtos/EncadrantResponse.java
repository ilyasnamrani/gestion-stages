package org.example.entrepriseservice.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncadrantResponse implements Serializable {
    private Long idEncadrant;
    private String nomEncadrant;
    private String prenomEncadrant;
    private String telephoneEncadrant;
    private String emailEncadrant;
    private String nomEntreprise;
    private List<String> offresStage ;
}

