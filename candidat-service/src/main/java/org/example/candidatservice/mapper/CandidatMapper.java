package org.example.candidatservice.mapper;

import org.example.candidatservice.dtos.CandidatRequest;
import org.example.candidatservice.dtos.CandidatResponse;
import org.example.candidatservice.dtos.CandidatResponseV2;
import org.example.candidatservice.entities.Candidat;

public interface CandidatMapper {
    public CandidatResponse toResponse(Candidat candidat);
    public CandidatResponseV2 toResponseV2(Candidat candidat);

    public Candidat toCandidat(CandidatRequest candidatRequest);

}
