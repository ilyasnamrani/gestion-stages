package org.example.entrepriseservice.services;

import org.example.entrepriseservice.dtos.*;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.mappers.EncadrantMapper;
import org.example.entrepriseservice.mappers.EntrepriseMapper;
import org.example.entrepriseservice.mappers.OffreStageMapper;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntrepriseServiceImp implements EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final EncadrantMapper encadrantMapper;
    private final OffreStageMapper offreStageMapper;
    private final OffreStageRepository offreStageRepository;
    private final EncadrantRepository encadrantRepository;
    public EntrepriseServiceImp(EntrepriseRepository entrepriseRepository,
                                EntrepriseMapper entrepriseMapper,
                                EncadrantMapper encadrantMapper,
                                OffreStageMapper offreStageMapper,
                                EncadrantRepository encadrantRepository,
                                OffreStageRepository offreStageRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.encadrantMapper = encadrantMapper;
        this.offreStageMapper = offreStageMapper;
        this.offreStageRepository = offreStageRepository;
        this.encadrantRepository = encadrantRepository;
    }

    @Override
    @Cacheable(value = "entreprise", key = "#id")
    public EntrepriseResponse getEntrepriseV1(Long id) {
        return entrepriseMapper.toEntrepriseResponse(entrepriseRepository.findById(id).get());
    }

    @Override
    @Cacheable(value = "entrepriseV2", key = "#id")
    public EntrepriseResponseV2 getEntrepriseV2(Long id) {
        return entrepriseMapper.toEntrepriseResponseV2(entrepriseRepository.findById(id).get());
    }

    @Override
    public EntrepriseResponse createEntreprise(EntrepriseRequest entrepriseRequest) {

        return
                entrepriseMapper.toEntrepriseResponse(entrepriseRepository
                        .save(entrepriseMapper.toEntreprise(entrepriseRequest)));
    }

    @Override
    public EntrepriseResponseV2 updateEntreprise(Long id, EntrepriseRequest entrepriseRequest) {

        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée avec id " + id));

        if (entrepriseRequest.getAdresseEntreprise() != null) {
            entreprise.setAdresseEntreprise(entrepriseRequest.getAdresseEntreprise());
        }

        if (entrepriseRequest.getNomEntreprise() != null) {
            entreprise.setNomEntreprise(entrepriseRequest.getNomEntreprise());
        }

        if (entrepriseRequest.getOffresStage() != null) {
            for (Long offreId : entrepriseRequest.getOffresStage()) {
                OffreStage offre = offreStageRepository.findById(offreId)
                        .orElseThrow(() -> new RuntimeException("OffreStage non trouvée : " + offreId));

                if (!entreprise.getOffresStage().contains(offre)) {
                    entreprise.getOffresStage().add(offre);
                }
            }
        }

        if (entrepriseRequest.getEncadrants() != null) {
            for (Long encadrantId : entrepriseRequest.getEncadrants()) {
                Encadrant enc = encadrantRepository.findById(encadrantId)
                        .orElseThrow(() -> new RuntimeException("Encadrant non trouvé : " + encadrantId));

                if (!entreprise.getEncadrants().contains(enc)) {
                    entreprise.getEncadrants().add(enc);
                }
            }
        }

        Entreprise saved = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toEntrepriseResponseV2(saved);
    }



    @Override
    public void deleteEntreprise(Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id).get();
        entreprise.getEncadrants().forEach(e-> e.setEntreprise(null));
        entreprise.getOffresStage().forEach(e-> e.setEntreprise(null));
        entrepriseRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "entreprises")
    public List<EntrepriseResponse> getAllEntreprisesV1() {
        List<EntrepriseResponse> entrepriseResponses = new ArrayList<>();
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        for (Entreprise entreprise : entrepriseList) {
            entrepriseResponses.add(entrepriseMapper.toEntrepriseResponse(entreprise));
        }
        return  entrepriseResponses ;
    }

    @Override
    @Cacheable(value = "entreprisesV2")
    public List<EntrepriseResponseV2> getAllEntreprisesV2() {
        List<EntrepriseResponseV2> entrepriseResponses = new ArrayList<>();
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        for (Entreprise entreprise : entrepriseList) {
            entrepriseResponses.add(entrepriseMapper.toEntrepriseResponseV2(entreprise));
        }
        return  entrepriseResponses ;
    }

    @Override
    public List<EncadrantResponse> getAllEncadrant() {
        List<EncadrantResponse> encadrantResponses = new ArrayList<>();
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        for (Entreprise entreprise : entrepriseList) {
            entreprise.getEncadrants().forEach(encadrant
                    -> encadrantResponses.add(encadrantMapper.toEncadrantResponse(encadrant)));
        }
        return encadrantResponses;
    }

    @Override
    public List<OffreStageResponse> getAllOffreStage() {
        List<OffreStageResponse> offreStageResponses = new ArrayList<>();
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        for (Entreprise entreprise : entrepriseList) {
            entreprise.getOffresStage().forEach(offreStage
                    -> offreStageResponses.add(offreStageMapper.toOffreStageResponse(offreStage)));
        }
        return offreStageResponses;
    }
}
