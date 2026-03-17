package org.example.entrepriseservice.dtos;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncadrantRequest {
    private String nomEncadrant;
    private String prenomEncadrant;
    private String telephoneEncadrant;
    private String emailEncadrant;
    private Long entreprise;
    private List<Long> offresStage ;
}

