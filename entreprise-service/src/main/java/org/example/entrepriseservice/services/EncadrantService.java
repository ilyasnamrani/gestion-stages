package org.example.entrepriseservice.services;

import org.example.entrepriseservice.dtos.EncadrantRequest;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageResponse;

import java.util.List;

public interface EncadrantService {
        public List<EncadrantResponse> getAllEncadrants();
        public EncadrantResponse getEncadrant(Long id);
        public EncadrantResponse create(EncadrantRequest encadrantRequest);
        public EncadrantResponse update(EncadrantRequest encadrantRequest,Long id);
        public List<OffreStageResponse> getOffresStagesEncadrant(Long idEncadrant);
        public EntrepriseResponseV2 getEntreprise(Long idEncadrant);
        public void delete(Long id);
}
