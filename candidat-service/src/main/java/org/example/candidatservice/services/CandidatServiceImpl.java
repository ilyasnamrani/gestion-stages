package org.example.candidatservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.candidatservice.dtos.CandidatRequest;
import org.example.candidatservice.dtos.CandidatResponse;
import org.example.candidatservice.entities.Candidat;
import org.example.candidatservice.mapper.CandidatMapper;
import org.example.candidatservice.models.OffreStage;
import org.example.candidatservice.repositories.CandidatRepository;
import org.example.candidatservice.web.EntrepriseFeignClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatServiceImpl implements CandidatService {

    private final CandidatRepository candidatRepository;
    private final CandidatMapper candidatMapper;
    private final EntrepriseFeignClient entrepriseFeignClient;
    private final KafkaProducerService kafkaProducerService;

    public CandidatServiceImpl(CandidatRepository candidatRepository,
            CandidatMapper candidatMapper,
            EntrepriseFeignClient entrepriseFeignClient,
            KafkaProducerService kafkaProducerService) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
        this.entrepriseFeignClient = entrepriseFeignClient;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @Cacheable(value = "candidat", key = "#id")
    public CandidatResponse getCandidat(Long id) {
        Candidat candidat = candidatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        return candidatMapper.toResponse(candidat);
    }

    @Override
    public List<CandidatResponse> getCandidatsPure() {
        return candidatRepository.findAll().stream()
                .map(candidatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value ="cache-candidats")
    @Override
    public List<CandidatResponse> getCandidats() {
        return candidatRepository.findAll().stream()
                .map(candidatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CandidatResponse createCandidat(@Valid CandidatRequest candidatRequest) {
        CandidatResponse response = candidatMapper.toResponse(
                candidatRepository.save(candidatMapper.toCandidat(candidatRequest)));
        kafkaProducerService.sendCandidateCreatedEvent(response);
        return response;
    }

    @Override
    public void deleteCandidat(Long id) {
        candidatRepository.deleteById(id);
    }

    @Override
    public CandidatResponse updateCandidat(CandidatRequest candidat, Long id) {
        Candidat updated = candidatRepository.findById(id)
                .map(existing -> {
                    existing.setNomCandidat(candidat.getNomCandidat());
                    existing.setPrenomCandidat(candidat.getPrenomCandidat());
                    existing.setAdresseCandidat(candidat.getAdresseCandidat());
                    existing.setTelephoneCandidat(candidat.getTelephoneCandidat());
                    existing.setEmailCandidat(candidat.getEmailCandidat());
                    existing.setFiliere(candidat.getFiliere());
                    existing.setNiveau(candidat.getNiveau());
                    existing.setCompetences(candidat.getCompetences());
                    return candidatRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Candidat non trouvé avec id " + id));

        CandidatResponse response = candidatMapper.toResponse(updated);
        kafkaProducerService.sendCandidateUpdatedEvent(response);
        return response; // no second save
    }

    @Override
    @Cacheable(value = "candidat-offres-pos", key = "#idCandidat")
    public List<OffreStage> offresStagePos(Long idCandidat) {
        Candidat c = candidatRepository.findById(idCandidat)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        List<OffreStage> offreStagePos = new ArrayList<>();
        if (c.getIdOffresStagePos() != null) {
            for (Long po : c.getIdOffresStagePos()) {
                offreStagePos.add(entrepriseFeignClient.getOffreStageById(po));
            }
        }
        return offreStagePos;
    }

    @Override
    public void postuler(Long idOffre, Long idCandidat) {
        Candidat candidat = candidatRepository.findById(idCandidat)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        OffreStage offreStage = entrepriseFeignClient.getOffreStageById(idOffre);
        if (offreStage != null) {
            candidat.getIdOffresStagePos().add(idOffre);
            candidatRepository.save(candidat); // persist the change
        }
    }

    @Override
    public void annulerPostule(Long idOffre, Long idCandidat) {
        Candidat candidat = candidatRepository.findById(idCandidat)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        OffreStage offreStage = entrepriseFeignClient.getOffreStageById(idOffre);
        if (offreStage != null) {
            candidat.getIdOffresStagePos().remove(idOffre);
            candidatRepository.save(candidat); // persist the change
        }
    }

    @Override
    public void accepterCandidat(Long idCandidat, Long idOffre) {
        Candidat c = candidatRepository.findById(idCandidat)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        c.getIdOffresStageAccepted().add(idOffre);
        candidatRepository.save(c); // persist the change
    }

    @Override
    @Cacheable(value = "candidat-offres-accepted", key = "#idCandidat")
    public List<OffreStage> offresStageAccepted(Long idCandidat) {
        Candidat c = candidatRepository.findById(idCandidat)
                .orElseThrow(() -> new EntityNotFoundException("Candidat not found"));
        List<OffreStage> offreStageAccepted = new ArrayList<>();
        if (c.getOffresStageAccepted() != null) {
            for (Long ac : c.getIdOffresStageAccepted()) {
                offreStageAccepted.add(entrepriseFeignClient.getOffreStageById(ac));
            }
        }
        return offreStageAccepted;
    }

    @Override
    public List<OffreStage> offresStageDispo() {
        return entrepriseFeignClient.getAllOffreStagePure();
    }
}