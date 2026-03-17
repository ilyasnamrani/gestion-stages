package org.example.entrepriseservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.entrepriseservice.dtos.EncadrantRequest;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.mappers.EncadrantMapper;
import org.example.entrepriseservice.mappers.EntrepriseMapper;
import org.example.entrepriseservice.mappers.OffreStageMapper;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EncadrantServiceImp implements EncadrantService{
    private final EncadrantRepository encadrantRepository;
    private final EncadrantMapper encadrantMapper;
    private final OffreStageMapper offreStageMapper;
    private final OffreStageRepository offreStageRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final EntrepriseRepository entrepriseRepository;
    public EncadrantServiceImp(EncadrantRepository encadrantRepository,
                               EncadrantMapper encadrantMapper,
                               EntrepriseRepository entrepriseRepository,
                               OffreStageMapper offreStageMapper,
                               EntrepriseMapper entrepriseMapper,
                               OffreStageRepository offreStageRepository) {
        this.encadrantRepository = encadrantRepository;
        this.encadrantMapper = encadrantMapper;
        this.entrepriseRepository = entrepriseRepository;
        this.offreStageMapper = offreStageMapper;
        this.entrepriseMapper = entrepriseMapper;
        this.offreStageRepository = offreStageRepository;
    }
    @Override
    public List<EncadrantResponse> getAllEncadrants() {
        List<EncadrantResponse> encadrantResponseList = new ArrayList<>();
        List<Encadrant> encadrants = encadrantRepository.findAll();
        for (Encadrant encadrant : encadrants) {
            encadrantResponseList.add(encadrantMapper.toEncadrantResponse(encadrant));
        }

        return encadrantResponseList;
    }

    @Override
    public EncadrantResponse getEncadrant(Long id) {
        return encadrantMapper.toEncadrantResponse(encadrantRepository.findById(id).get());
    }

    @Override
    public EncadrantResponse create(EncadrantRequest encadrantRequest) {

        return encadrantMapper.toEncadrantResponse(encadrantRepository
                .save(encadrantMapper.toEncadrant(encadrantRequest)));
    }

    @Override
    public EncadrantResponse update(EncadrantRequest encadrantRequest, Long id) {

        Encadrant encadrant = encadrantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encadrant introuvable avec id " + id));

        if (encadrantRequest.getEmailEncadrant() != null) {
            encadrant.setEmailEncadrant(encadrantRequest.getEmailEncadrant());
        }

        if (encadrantRequest.getNomEncadrant() != null) {
            encadrant.setNomEncadrant(encadrantRequest.getNomEncadrant());
        }

        if (encadrantRequest.getPrenomEncadrant() != null) {
            encadrant.setPrenomEncadrant(encadrantRequest.getPrenomEncadrant());
        }

        if (encadrantRequest.getTelephoneEncadrant() != null) {
            encadrant.setTelephoneEncadrant(encadrantRequest.getTelephoneEncadrant());
        }

        if (encadrantRequest.getEntreprise() != null) {
            Entreprise entreprise = entrepriseRepository.findById(encadrantRequest.getEntreprise())
                    .orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable"));
            encadrant.setEntreprise(entreprise);
        }

        if (encadrantRequest.getOffresStage() != null) {
            List<OffreStage> offreStages = new ArrayList<OffreStage>();
            for(Long offreStage : encadrantRequest.getOffresStage()) {
                OffreStage offre = offreStageRepository.findById(offreStage).orElseThrow(
                        () -> new RuntimeException("OffreStage introuvable"));
                offreStages.add(offre);
            }
            for (OffreStage offreStage : offreStages) {
                if (!encadrant.getOffresStage().contains(offreStage)) {
                    encadrant.getOffresStage().add(offreStage);
                }
            }
        }

        return encadrantMapper.toEncadrantResponse(encadrantRepository.save(encadrant));
    }

    @Override
    public List<OffreStageResponse> getOffresStagesEncadrant(Long idEncadrant) {
        List<OffreStageResponse> offreStageResponseList = new ArrayList<>();
        Encadrant encadrant = encadrantRepository.findById(idEncadrant)
                .orElseThrow(() -> new EntityNotFoundException("Encadrant introuvable avec id " + idEncadrant));
        encadrant.getOffresStage().forEach(offre -> offreStageResponseList.add(offreStageMapper.toOffreStageResponse(offre)));
        return offreStageResponseList;
    }


    @Override
    public EntrepriseResponseV2 getEntreprise(Long idEncadrant) {
        Encadrant encadrant = encadrantRepository.findById(idEncadrant)
                .orElseThrow(() -> new EntityNotFoundException("Encadrant introuvable avec id " + idEncadrant));
        return entrepriseMapper.toEntrepriseResponseV2(encadrant.getEntreprise());
    }

    @Override
    public void delete(Long id) {
        Encadrant encadrant = encadrantRepository.findById(id).get();
        encadrant.setEntreprise(null);
        encadrant.getOffresStage().forEach(offre
                -> {
            offre.setEncadrant(null);
            offre.setEntreprise(null);
        });
        encadrantRepository.deleteById(id);
    }
}
