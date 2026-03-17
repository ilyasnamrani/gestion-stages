package org.example.candidatservice.mapper;

import org.example.candidatservice.dtos.CandidatRequest;
import org.example.candidatservice.dtos.CandidatResponse;
import org.example.candidatservice.dtos.CandidatResponseV2;
import org.example.candidatservice.entities.Candidat;
import org.example.candidatservice.models.OffreStage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CandidatMapperImpl implements CandidatMapper {
    @Override
    public CandidatResponse toResponse(Candidat candidat) {
        CandidatResponse candidatResponse = new CandidatResponse();
        candidatResponse.setIdCandidat(candidat.getIdCandidat());
        candidatResponse.setEmailCandidat(candidat.getEmailCandidat());
        candidatResponse.setNomCandidat(candidat.getNomCandidat());
        candidatResponse.setPrenomCandidat(candidat.getPrenomCandidat());
        candidatResponse.setNiveau(candidat.getNiveau());
        candidatResponse.setFiliere(candidat.getFiliere());
        candidatResponse.setIdOffresStagePos(candidat.getIdOffresStagePos());
        candidatResponse.setIdOffresStageAccepted(candidat.getIdOffresStageAccepted());

        if (candidat.getEncardant() != null) {
            candidatResponse.setInfosEncadrant(
                    candidat.getEncardant().getNomEncadrant() + " " +
                            candidat.getEncardant().getPrenomEncadrant()
            );
        }

        List<String> titresPos = new ArrayList<>();
        if (candidat.getOffresStagePos() != null) {
            for (OffreStage offre : candidat.getOffresStagePos()) {
                titresPos.add(offre.getTitreOffre());
            }
        }
        candidatResponse.setTitresStagesPos(titresPos);

        List<String> titresAccepted = new ArrayList<>();
        if (candidat.getOffresStageAccepted() != null) {
            for (OffreStage offre : candidat.getOffresStageAccepted()) {
                titresAccepted.add(offre.getTitreOffre());
            }
        }
        candidatResponse.setTitresStagesAccepted(titresAccepted);

        return candidatResponse;
    }

    @Override
    public CandidatResponseV2 toResponseV2(Candidat candidat) {
        CandidatResponseV2 candidatResponseV2 = new CandidatResponseV2();
        candidatResponseV2.setEmailCandidat(candidat.getEmailCandidat());
        candidatResponseV2.setNomCandidat(candidat.getNomCandidat());
        candidatResponseV2.setPrenomCandidat(candidat.getPrenomCandidat());
        candidatResponseV2.setNiveau(candidat.getNiveau());
        candidatResponseV2.setFiliere(candidat.getFiliere());
        candidatResponseV2
                .setInfosEncadrant(candidat.getEncardant()
                .getNomEncadrant() + " " + candidat.getEncardant().getPrenomEncadrant());
        List<String> offresStagePos = new ArrayList<String>();
        List<String> offresStageAccepted = new ArrayList<String>();

        for (OffreStage offre : candidat.getOffresStagePos()) {
            offresStagePos.add(offre.getTitreOffre());
        }
        candidatResponseV2.setTitresStagesPos(offresStagePos);

        for (OffreStage offre : candidat.getOffresStageAccepted()) {
            offresStageAccepted.add(offre.getTitreOffre());
        }
        candidatResponseV2.setTitresStagesAccepted(offresStageAccepted);
        return candidatResponseV2;
    }

    @Override
    public Candidat toCandidat(CandidatRequest candidatRequest) {
        Candidat candidat = new Candidat();
        candidat.setEmailCandidat(candidatRequest.getEmailCandidat());
        candidat.setNomCandidat(candidatRequest.getNomCandidat());
        candidat.setPrenomCandidat(candidatRequest.getPrenomCandidat());
        candidat.setAdresseCandidat(candidatRequest.getAdresseCandidat());
        candidat.setTelephoneCandidat(candidatRequest.getTelephoneCandidat());
        candidat.setNiveau(candidatRequest.getNiveau());
        candidat.setFiliere(candidatRequest.getFiliere());
        candidat.setCompetences(candidatRequest.getCompetences());
        return candidat;
    }


}
