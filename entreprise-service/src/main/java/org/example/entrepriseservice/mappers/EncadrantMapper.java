package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.EncadrantRequest;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.entities.Encadrant;

public interface EncadrantMapper {
    public Encadrant toEncadrant(EncadrantRequest encadrantRequest);
    public EncadrantResponse toEncadrantResponse(Encadrant encadrant);
}
