package org.example.entrepriseservice.web;

import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageRequest;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.entities.OffreStage;
import org.example.entrepriseservice.mappers.OffreStageMapper;
import org.example.entrepriseservice.models.Candidat;
import org.example.entrepriseservice.repositories.OffreStageRepository;
import org.example.entrepriseservice.services.OffreStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offreStage")
public class OffreStageController {

    private final OffreStageService offreStageService;
    private final OffreStageMapper offreStageMapper;
    private final  OffreStageRepository offreStageRepository;
    private final  CandidatFeignClient candidatFeignClient;

    public OffreStageController(OffreStageService offreStageService,
                                OffreStageMapper offreStageMapper,
                                CandidatFeignClient candidatFeignClient,
                                OffreStageRepository offreStageRepository){
        this.offreStageService = offreStageService;
        this.offreStageMapper = offreStageMapper;
        this.candidatFeignClient = candidatFeignClient;
        this.offreStageRepository = offreStageRepository;

    }



    @GetMapping("/all")
    public List<OffreStageResponse> getAllOffreStage(){

        Map<Long, Candidat> candidatMap = candidatFeignClient.getCandidatsPure()
                .stream()
                .collect(Collectors.toMap(Candidat::getIdCandidat, c -> c));

        List<OffreStage> offresStage = offreStageRepository.findAll();

        offresStage.forEach(offreStage -> {

            List<Candidat> candidats = Optional.ofNullable(offreStage.getIdCandidats())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(candidatMap::get)
                    .filter(Objects::nonNull)
                    .toList();

            offreStage.setCandidats(candidats);
        });
        List<OffreStageResponse> responses = new ArrayList<>();
        offresStage.forEach(offreStage -> responses.add(offreStageMapper.toOffreStageResponse(offreStage)));
        return responses;
    }

    @GetMapping("/all/pure")
    public List<OffreStageResponse> getAllOffreStagePure(){
        List<OffreStage> offresStage = offreStageRepository.findAll();
        List<OffreStageResponse> responses = new ArrayList<>();
        offresStage.forEach(offreStage -> responses.add(offreStageMapper.toOffreStageResponse(offreStage)));
        return responses;
    }

    @GetMapping("/{id}")
    public OffreStageResponse getOffreStageById(@PathVariable Long id){
        return offreStageService.getOffreStageById(id);
    }

    @PostMapping("/create")
    public OffreStageResponse createOffreStage(@RequestBody OffreStageRequest offreStageRequest){
        return offreStageService.createOffreStage(offreStageRequest);
    }

    @PutMapping("/{id}/update")
    public OffreStageResponse updateOffreStage(@PathVariable Long id, @RequestBody OffreStageRequest offreStageRequest){
        return offreStageService.updateOffreStage(id, offreStageRequest);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteOffreStage(@PathVariable Long id){
        offreStageService.deleteOffreStage(id);
    }

    @GetMapping("/{id}/encadrant")
    public EncadrantResponse getEncadrant(@PathVariable Long id){
        return offreStageService.getEncadrantByOffre(id);
    }


    @GetMapping("/{id}/entreprise")
    public EntrepriseResponseV2 getEntreprise(@PathVariable Long id){
        return offreStageService.getEntrepriseByOffre(id);
    }

//    @GetMapping("/candidats")
//    public List<Candidat> getCandidats(){
//        return candidatFeignClient.getCandidats();
//    }

    @GetMapping("/candidat/{id}")
    public Candidat getCandidat(@PathVariable Long id){
        return candidatFeignClient.getCandidatV1(id);
    }

    @PostMapping("/candidatAccepted")
    public void accepterCandidat(@RequestParam Long idCandidat ,@RequestParam Long idOffre){
        offreStageService.accepterCandidat(idCandidat,idOffre);
    }

//    @GetMapping("/postuler/{offre}/{candidat}")
//    public OffreStageResponse postuler(@PathVariable Long offre,@PathVariable Long candidat){
//        return offreStageService.postuler(offre,candidat);
//    }

}
