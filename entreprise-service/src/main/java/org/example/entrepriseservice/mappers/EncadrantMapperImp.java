package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.EncadrantRequest;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EncadrantMapperImp implements EncadrantMapper{
    private final EntrepriseRepository entrepriseRepository;
    private final OffreStageRepository offreStageRepository;
    public EncadrantMapperImp(EntrepriseRepository entrepriseRepository,OffreStageRepository offreStageRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.offreStageRepository = offreStageRepository;
    }

    @Override
    public Encadrant toEncadrant(EncadrantRequest encadrantRequest) {
        Encadrant encadrant = new Encadrant();
        encadrant.setEmailEncadrant(encadrantRequest.getEmailEncadrant());
        encadrant.setNomEncadrant(encadrantRequest.getNomEncadrant());
        encadrant.setPrenomEncadrant(encadrantRequest.getPrenomEncadrant());
        encadrant.setTelephoneEncadrant(encadrantRequest.getTelephoneEncadrant());
        Entreprise entreprise = entrepriseRepository.findById(encadrantRequest.getEntreprise()).orElseThrow(
                ()-> new RuntimeException("entreprise not found")
        );
        encadrant.setEntreprise(entreprise);
        List<OffreStage> offreStageList = new ArrayList<OffreStage>();
        encadrantRequest.getOffresStage().forEach(
                offreStageRequest -> {
                    OffreStage offreStage = offreStageRepository.findById(offreStageRequest).orElseThrow(
                            ()-> new RuntimeException("offreStage not found")
                    );
                    offreStageList.add(offreStage);
                }
        );
        encadrant.setOffresStage(offreStageList);
        return encadrant;
    }

    @Override
    public EncadrantResponse toEncadrantResponse(Encadrant encadrant) {
        EncadrantResponse encadrantResponse = new EncadrantResponse();
        encadrantResponse.setIdEncadrant(encadrant.getIdEncadrant());
        encadrantResponse.setEmailEncadrant(encadrant.getEmailEncadrant());
        encadrantResponse.setNomEncadrant(encadrant.getNomEncadrant());
        encadrantResponse.setPrenomEncadrant(encadrant.getPrenomEncadrant());
        encadrantResponse.setTelephoneEncadrant(encadrant.getTelephoneEncadrant());
        List<String> offres = new ArrayList<>();
        encadrant.getOffresStage().forEach(offreStage->offres.add(offreStage.getTitreOffre()));
        encadrantResponse.setOffresStage(offres);
        encadrantResponse.setNomEntreprise(encadrant.getEntreprise().getNomEntreprise());

        return encadrantResponse;
    }
}
