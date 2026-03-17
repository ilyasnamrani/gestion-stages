package org.example.candidatservice.services;

import org.example.candidatservice.dtos.CandidatRequest;
import org.example.candidatservice.dtos.CandidatResponse;
import org.example.candidatservice.models.OffreStage;

import java.util.List;

public interface CandidatService {
    public CandidatResponse getCandidat(Long id);
    public List<CandidatResponse> getCandidatsPure();
    public List<CandidatResponse> getCandidats();
    public  CandidatResponse createCandidat(CandidatRequest candidatRequest);
    public void deleteCandidat(Long id);
    public CandidatResponse updateCandidat(CandidatRequest candidat,Long id);
    public List<OffreStage> offresStageAccepted(Long idCandidat);
    public List<OffreStage> offresStagePos(Long idCandidat);
    public List<OffreStage> offresStageDispo();
    public void postuler(Long idOffre,Long idCandidat);
    public void annulerPostule(Long idOffre,Long idCandidat);
    public void accepterCandidat(Long idCandidat,Long idOffre);
    //public void  contacterEncadrant(Long id,String message);
     //public void contacterEntreprise(Long id,String message);
}
