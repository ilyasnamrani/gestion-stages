package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.OffreStageRequest;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.entities.OffreStage;

public interface OffreStageMapper {
    public OffreStage toOffreStage(OffreStageRequest offreStageRequest);
    public OffreStageResponse toOffreStageResponse(OffreStage offreStage);
}
