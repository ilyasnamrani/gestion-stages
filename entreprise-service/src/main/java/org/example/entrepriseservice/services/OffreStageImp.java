package org.example.entrepriseservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageRequest;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.mappers.EncadrantMapper;
import org.example.entrepriseservice.mappers.EntrepriseMapper;
import org.example.entrepriseservice.mappers.OffreStageMapper;
import org.example.entrepriseservice.models.Candidat;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.example.entrepriseservice.web.CandidatFeignClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OffreStageImp implements OffreStageService {
    private final OffreStageRepository offreStageRepository;
    private final OffreStageMapper offreStageMapper;
    private final EncadrantMapper encadrantMapper;
    private final EncadrantRepository encadrantRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final CandidatFeignClient candidatFeignClient;

    public OffreStageImp(OffreStageRepository offreStageRepository,
                         OffreStageMapper offreStageMapper,
                         EncadrantMapper encadrantMapper,
                         EntrepriseMapper entrepriseMapper,
                         EntrepriseRepository entrepriseRepository,
                         EncadrantRepository encadrantRepository, CandidatFeignClient candidatFeignClient) {
        this.offreStageRepository = offreStageRepository;
        this.offreStageMapper = offreStageMapper;
        this.encadrantMapper = encadrantMapper;
        this.entrepriseMapper = entrepriseMapper;
        this.entrepriseRepository = entrepriseRepository;
        this.encadrantRepository = encadrantRepository;
        this.candidatFeignClient = candidatFeignClient;
    }
    @Override
    @Cacheable(value = "offre-stage", key = "#id")
    public OffreStageResponse getOffreStageById(Long id) {

        OffreStage offre = offreStageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Offre stage introuvable : " + id));

        // Initialisation si nécessaire
        if (offre.getCandidats() == null) {
            offre.setCandidats(new ArrayList<>());
        }

        List<Candidat> candidats = candidatFeignClient.getCandidatsPure();

        // convertir la liste des IDs en Set pour optimiser la recherche
        // HashSet est une implementation de l interface set il sert a eliminer les
        //doublant [1,2,2,3] -> [1,2,3]
        Set<Long> idsCandidats = new HashSet<>(offre.getIdCandidats());

        for (Candidat candidat : candidats) {
            if (idsCandidats.contains(candidat.getIdCandidat())) {
                offre.getCandidats().add(candidat);
            }
        }

        return offreStageMapper.toOffreStageResponse(offre);
    }
    @Cacheable("cache-offres-Stage")
    @Override
    public List<OffreStageResponse> getOffresStage() {
        List<OffreStage> offreStages = offreStageRepository.findAll();
        List<OffreStageResponse> offreStageResponse = new ArrayList<>();
        for (OffreStage offreStage : offreStages) {
            offreStageResponse.add(offreStageMapper.toOffreStageResponse(offreStage));
        }
        return offreStageResponse;
    }

    @Override
    public OffreStageResponse createOffreStage(OffreStageRequest offreStageRequest) {
        return offreStageMapper.toOffreStageResponse(offreStageRepository.save(offreStageMapper.toOffreStage(offreStageRequest)));
    }

    @Override
    public OffreStageResponse updateOffreStage(Long id, OffreStageRequest offreStageRequest) {
        OffreStage offreStage = offreStageRepository.findById(id).get();
        if (offreStageRequest.getTitreOffre() != null) {
            offreStage.setTitreOffre(offreStageRequest.getTitreOffre());
        }
        if (offreStageRequest.getDescriptionOffre() != null) {
            offreStage.setDescriptionOffre(offreStageRequest.getDescriptionOffre());
        }
        if (offreStageRequest.getEncadrant() != null) {
            Encadrant e = encadrantRepository.findById(offreStageRequest.getEncadrant()).orElseThrow(() -> new EntityNotFoundException("Encadrant introuvable"));
            offreStage.setEncadrant(e);
        }
        if (offreStageRequest.getEntreprise() != null) {
            Entreprise ep = entrepriseRepository.findById(offreStageRequest.getEntreprise()).orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable"));
            offreStage.setEntreprise(ep);
        }
        return offreStageMapper.toOffreStageResponse(offreStageRepository.save(offreStage));
    }

    @Override
    public void deleteOffreStage(Long id) {
        OffreStage offreStage = offreStageRepository.findById(id).get();
        offreStage.setEntreprise(null);
        offreStage.setEncadrant(null);
        offreStageRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "encadrant-offre", key = "#idOffre")
    public EncadrantResponse getEncadrantByOffre(Long idOffre) {
        OffreStage offre =  offreStageRepository.findById(idOffre).get();
        Encadrant encadrant = offre.getEncadrant();
        return encadrantMapper.toEncadrantResponse(encadrant);
    }

    @Override
    @Cacheable(value = "entreprise-offre", key = "#idOffre")
    public EntrepriseResponseV2 getEntrepriseByOffre(Long idOffre) {
        OffreStage offre =  offreStageRepository.findById(idOffre).get();
        Entreprise entreprise = offre.getEntreprise();
        return   entrepriseMapper.toEntrepriseResponseV2(entreprise);

    }

    @Override
    public void accepterCandidat(Long idOffre, Long idCandidat) {
        OffreStage offreStage = offreStageRepository.findById(idOffre).orElseThrow(
                () -> new EntityNotFoundException("Offre introuvable"));
        offreStage.getIdCandidats().add(idCandidat);
        candidatFeignClient.accpterCandidat(idCandidat,idOffre);
    }





}
