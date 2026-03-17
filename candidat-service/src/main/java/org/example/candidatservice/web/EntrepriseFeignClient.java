package org.example.candidatservice.web;

import org.example.candidatservice.models.Encadrant;
import org.example.candidatservice.models.Entreprise;
import org.example.candidatservice.models.OffreStage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "ENTREPRISE-SERVICE")
public interface EntrepriseFeignClient {
    @GetMapping("/offreStage/all/pure")
    public List<OffreStage> getAllOffreStagePure();
    @GetMapping("/offreStage/{id}")
    public OffreStage getOffreStageById(@PathVariable Long id);
    @GetMapping("/encadrant/all")
    public List<Encadrant> getAllEncadrant();
    @GetMapping("/encadrant/{id}")
    public Encadrant getEncadrantById(@PathVariable Long id);
    @GetMapping("/entreprise/v2/all")
    public List<Entreprise> getAllEntrepriseV2();
    @GetMapping("/entreprise/{id}/v2")
    public Entreprise getEntrepriseV2(@PathVariable Long id);

}
