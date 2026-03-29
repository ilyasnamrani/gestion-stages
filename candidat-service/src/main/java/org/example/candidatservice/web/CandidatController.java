package org.example.candidatservice.web;

import jakarta.persistence.EntityNotFoundException;
import org.example.candidatservice.dtos.CandidatRequest;
import org.example.candidatservice.dtos.CandidatResponse;
import org.example.candidatservice.entities.Candidat;
import org.example.candidatservice.mapper.CandidatMapper;
import org.example.candidatservice.models.Encadrant;
import org.example.candidatservice.models.OffreStage;
import org.example.candidatservice.repositories.CandidatRepository;
import org.example.candidatservice.services.CandidatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidats")
public class CandidatController {
    private final CandidatService candidatService;
    private final CandidatMapper candidatMapper;
    private final EntrepriseFeignClient entrepriseFeignClient;
    private final CandidatRepository candidatRepository;

    public CandidatController(CandidatService candidatService,
            CandidatMapper candidatMapper,
            EntrepriseFeignClient entrepriseFeignClient,
            CandidatRepository candidatRepository) {
        this.candidatService = candidatService;
        this.candidatMapper = candidatMapper;
        this.entrepriseFeignClient = entrepriseFeignClient;
        this.candidatRepository = candidatRepository;
    }

    @GetMapping("/all")
    public List<CandidatResponse> getCandidats() {

        List<OffreStage> offresStage = entrepriseFeignClient.getAllOffreStagePure();
        List<Encadrant> encadrants = entrepriseFeignClient.getAllEncadrant();
        List<Candidat> candidats = candidatRepository.findAll();

        Map<Long, OffreStage> offreMap = offresStage.stream()
                .collect(Collectors.toMap(OffreStage::getIdOffreStage, o -> o));

        Map<Long, Encadrant> encadrantMap = encadrants.stream()
                .collect(Collectors.toMap(Encadrant::getIdEncadrant, e -> e));

        for (Candidat candidat : candidats) {

            candidat.setEncardant(
                    encadrantMap.get(candidat.getIdEncadrant()));

            List<OffreStage> offresPos = candidat.getIdOffresStagePos().stream()
                    .map(offreMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            candidat.setOffresStagePos(offresPos);

            List<OffreStage> offresAccepted = candidat.getIdOffresStageAccepted().stream()
                    .map(offreMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            candidat.setOffresStageAccepted(offresAccepted);
        }

        return candidats.stream()
                .map(candidatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/all/pure")
    public List<CandidatResponse> getCandidatsPure() {
        return candidatService.getCandidatsPure();
    }

    @GetMapping("/{id}/v1")
    public CandidatResponse getCandidatV1(@PathVariable Long id) {
        Candidat candidat = candidatRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("candidat "));
        List<OffreStage> offresStage = entrepriseFeignClient.getAllOffreStagePure();
        List<Encadrant> encadrants = entrepriseFeignClient.getAllEncadrant();

        // Transformer en Map pour accès rapide O(1)
        Map<Long, OffreStage> offreMap = offresStage.stream()
                .collect(Collectors.toMap(OffreStage::getIdOffreStage, o -> o));

        Map<Long, Encadrant> encadrantMap = encadrants.stream()
                .collect(Collectors.toMap(Encadrant::getIdEncadrant, e -> e));

        // Encadrant
        candidat.setEncardant(
                encadrantMap.get(candidat.getIdEncadrant()));

        // Offres postulées
        List<OffreStage> offresPos = candidat.getIdOffresStagePos()
                .stream().map(offreMap::get).filter(Objects::nonNull)
                .collect(Collectors.toList());
        candidat.setOffresStagePos(offresPos);

        // Offres acceptées
        List<OffreStage> offresAccepted = candidat.getIdOffresStageAccepted()
                .stream()
                .map(offreMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        candidat.setOffresStageAccepted(offresAccepted);

        return candidatMapper.toResponse(candidat);
    }
    //
    // @GetMapping("/{id}/v2")
    // public CandidatResponseV2 getCandidatV2(@PathVariable Long id){
    // Candidat candidat = candidatRepository.findById(id).orElseThrow(
    // () -> new EntityNotFoundException("candidat not found ")
    // );
    // return candidatMapper.toResponseV2(candidat);
    // }

    @PostMapping("/create")
    public CandidatResponse createCandidat(@RequestBody CandidatRequest candidatRequest) {
        return candidatService.createCandidat(candidatRequest);
    }

    @PutMapping("/update/{id}")
    public CandidatResponse updateCandidat(@RequestBody CandidatRequest candidatRequest, @PathVariable Long id) {
        return candidatService.updateCandidat(candidatRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
    }

    @GetMapping("/offresStage")
    public List<OffreStage> getOffresStage() {
        return entrepriseFeignClient.getAllOffreStagePure();
    }

    @GetMapping("/{id}/offresStage/accepted")
    public List<OffreStage> getOffresStageAccepted(@PathVariable Long id) {
        return candidatService.offresStageAccepted(id);
    }

    @GetMapping("/{id}/offresStage/pos")
    public List<OffreStage> getOffresStagePos(@PathVariable Long id) {
        return candidatService.offresStagePos(id);
    }

    @PostMapping("/{idOffre}/{idCandidat}/offreStage/postuler")
    public void postuler(@PathVariable Long idOffre, @PathVariable Long idCandidat) {
        candidatService.postuler(idOffre, idCandidat);
    }

    @PostMapping("/{idCandidat}/offreStage/{idOffre}/annulerPostule")
    public void annulerPostule(@PathVariable Long idOffre, @PathVariable Long idCandidat) {
        candidatService.annulerPostule(idOffre, idCandidat);
    }

    @PostMapping("/{idCandidat}/{idOffre}/accepted")
    public void accpterCandidat(@PathVariable Long idCandidat, @PathVariable Long idOffre) {
        candidatService.accepterCandidat(idCandidat, idOffre);
    }

}
