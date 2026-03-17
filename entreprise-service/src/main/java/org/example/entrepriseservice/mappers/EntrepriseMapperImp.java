package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.EntrepriseRequest;
import org.example.entrepriseservice.dtos.EntrepriseResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntrepriseMapperImp implements EntrepriseMapper {
    private final EncadrantRepository encadrantRepository;
    private final OffreStageRepository offreStageRepository;
    public EntrepriseMapperImp(EncadrantRepository encadrantRepository,OffreStageRepository offreStageRepository) {
        this.encadrantRepository = encadrantRepository;
        this.offreStageRepository = offreStageRepository;
    }
    @Override
    public Entreprise toEntreprise(EntrepriseRequest entrepriseRequest) {
        Entreprise entreprise = new Entreprise();
        entreprise.setAdresseEntreprise(entrepriseRequest.getAdresseEntreprise());
        entreprise.setNomEntreprise(entrepriseRequest.getNomEntreprise());
        List<Encadrant> encadrants = new ArrayList<Encadrant>();
        entrepriseRequest.getEncadrants().forEach(e
                ->{
            Encadrant encadrant1 = encadrantRepository.findById(e).orElseThrow(()->new RuntimeException("encadrant not found" + e));
            encadrants.add(encadrant1);
        } );
        entreprise.setEncadrants(encadrants);
        List<OffreStage> offres = new ArrayList<OffreStage>();
        entrepriseRequest.getOffresStage().forEach(o
                ->{
            OffreStage offre1 = offreStageRepository.findById(o).orElseThrow(()->new RuntimeException("Offre not found" + o));
            offres.add(offre1);
        } );
        entreprise.setOffresStage(offres);
        return entreprise;
    }

    @Override
    public EntrepriseResponse toEntrepriseResponse(Entreprise entreprise) {
        EntrepriseResponse entrepriseResponse = new EntrepriseResponse();
        entrepriseResponse.setIdEntreprise(entreprise.getIdEntreprise());
        entrepriseResponse.setNomEntreprise(entreprise.getNomEntreprise());
        entrepriseResponse.setAdresseEntreprise(entreprise.getAdresseEntreprise());
        List<Long> offresStage = new ArrayList<>();
        entreprise.getOffresStage().forEach(offreStage ->offresStage.add(offreStage.getIdOffreStage()));
        entrepriseResponse.setOffreStageList(offresStage);
        List<Long> encadrants = new ArrayList<>();
        entreprise.getEncadrants().forEach(encadrant -> encadrants.add(encadrant.getIdEncadrant()));
        entrepriseResponse.setEncadrantList(encadrants);
        return entrepriseResponse;
    }

    @Override
    public EntrepriseResponseV2 toEntrepriseResponseV2(Entreprise entreprise){
        EntrepriseResponseV2 entrepriseResponse = new EntrepriseResponseV2();
        entrepriseResponse.setIdEntreprise(entreprise.getIdEntreprise());
        entrepriseResponse.setNomEntreprise(entreprise.getNomEntreprise());
        entrepriseResponse.setAdresseEntreprise(entreprise.getAdresseEntreprise());

        List<String> encadrants = new ArrayList<>();
        entreprise.getEncadrants().forEach(encadrant ->
                encadrants.add(encadrant.getNomEncadrant() + " " + encadrant.getPrenomEncadrant())
        );

        List<String> offresStage = new ArrayList<>();
        entreprise.getOffresStage().forEach(offre ->
                offresStage.add(offre.getTitreOffre())
        );

        entrepriseResponse.setEncadrantList(encadrants);
        entrepriseResponse.setOffreStageList(offresStage);

        return entrepriseResponse;
    }

}
