package org.example.entrepriseservice.services;

import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageRequest;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OffreStageService {
    public OffreStageResponse getOffreStageById(@PathVariable Long id);
    public List<OffreStageResponse> getOffresStage();
    public OffreStageResponse createOffreStage(@RequestBody OffreStageRequest offreStageRequest);
    public OffreStageResponse updateOffreStage(@PathVariable Long id, @RequestBody OffreStageRequest offreStageRequest);
    public void  deleteOffreStage(@PathVariable Long id);
    public EncadrantResponse getEncadrantByOffre(Long idOffre);
    public EntrepriseResponseV2 getEntrepriseByOffre(Long idOffre);
   // public OffreStageResponse postuler(@PathVariable Long idOffre,@PathVariable Long idCandidat);
    public void accepterCandidat(Long idOffre,Long idCandidat);
}
