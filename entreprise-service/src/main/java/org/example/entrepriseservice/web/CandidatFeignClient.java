package org.example.entrepriseservice.web;

import org.example.entrepriseservice.models.Candidat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CANDIDAT-SERVICE")
public interface CandidatFeignClient {
    @GetMapping("candidats/all/pure")
    public List<Candidat> getCandidatsPure();

    @GetMapping("/candidats/{id}/v1")
    public Candidat getCandidatV1(@PathVariable Long id);

    @GetMapping("/candidats/{id}/v2")
    public Candidat getCandidatV2(@PathVariable Long id);

    @PostMapping("/candidats/{idCandidat}/{idOffre}/accepted")
    public void accpterCandidat( @PathVariable Long idCandidat,@PathVariable Long idOffre);

}
