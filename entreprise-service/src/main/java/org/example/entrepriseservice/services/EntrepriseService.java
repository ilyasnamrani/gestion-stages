package org.example.entrepriseservice.services;

import org.example.entrepriseservice.dtos.*;

import java.util.List;

public interface EntrepriseService {
    public EntrepriseResponse getEntrepriseV1(Long id);
    public EntrepriseResponseV2 getEntrepriseV2(Long id);
    public EntrepriseResponse createEntreprise(EntrepriseRequest entrepriseRequest);
    public EntrepriseResponseV2 updateEntreprise(Long id, EntrepriseRequest entrepriseRequest);
    public void  deleteEntreprise(Long id);
    public List<EntrepriseResponse> getAllEntreprisesV1();
    public List<EntrepriseResponseV2> getAllEntreprisesV2();
    public List<EncadrantResponse>  getAllEncadrant();
    public List<OffreStageResponse>  getAllOffreStage();
}
