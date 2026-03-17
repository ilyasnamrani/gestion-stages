package org.example.candidatservice.models;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Encadrant {
    private Long idEncadrant;
    private String nomEncadrant;
    private String prenomEncadrant;
    private String telephoneEncadrant;
    private String emailEncadrant;
    private String nomEntreprise;
    private List<String> offresStage;
}
