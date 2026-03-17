package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.OffreStageRequest;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.entities.Encadrant;
import org.example.entrepriseservice.entities.Entreprise;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.models.Candidat;
import org.example.entrepriseservice.repositories.EncadrantRepository;
import org.example.entrepriseservice.repositories.EntrepriseRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OffreStageMapperImp implements OffreStageMapper {
    private final EncadrantRepository encadrantRepository;
    private final EntrepriseRepository entrepriseRepository;
    public OffreStageMapperImp(EncadrantRepository encadrantRepository,
                               EntrepriseRepository entrepriseRepository) {
        this.encadrantRepository = encadrantRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public OffreStage toOffreStage(OffreStageRequest offreStageRequest) {
        OffreStage offreStage = new OffreStage();
        offreStage.setDateAnnonce(offreStageRequest.getDateAnnonce());
        offreStage.setDescriptionOffre(offreStageRequest.getDescriptionOffre());
        offreStage.setTitreOffre(offreStageRequest.getTitreOffre());
        Encadrant e = encadrantRepository.findById(offreStageRequest.getEncadrant()).orElseThrow(() -> new RuntimeException("Encadrant not found"));
        offreStage.setEncadrant(e);
        Entreprise ep = entrepriseRepository.findById(offreStageRequest.getEntreprise()).orElseThrow(() -> new RuntimeException("Entreprise not found"));
        offreStage.setEntreprise(ep);
        offreStage.setNombrePostules(0L);
        offreStage.setIdCandidats(offreStageRequest.getIdCandidats());
        return offreStage;
    }



    public OffreStageResponse toOffreStageResponse(OffreStage os) {
        OffreStageResponse dto = new OffreStageResponse();

        dto.setIdOffreStage(os.getIdOffreStage());
        dto.setTitreOffre(os.getTitreOffre());
        dto.setDateAnnonce(os.getDateAnnonce());
        dto.setDescriptionOffre(os.getDescriptionOffre());
        dto.setNombrePostules(os.getNombrePostules());
        dto.setNomEntreprise(os.getEntreprise().getNomEntreprise());
        dto.setNomEncadrant(os.getEncadrant().getNomEncadrant());
        dto.setPrenomEncadrant(os.getEncadrant().getPrenomEncadrant());

        List<String> nomCandidats = new ArrayList<String>();

        if (os.getCandidats() != null) {
            for (Candidat c : os.getCandidats()) {
                nomCandidats.add(c.getNomCandidat() + " " + c.getPrenomCandidat());
            }
        }

        dto.setNomCandidats(nomCandidats);
        return dto;
    }


}
