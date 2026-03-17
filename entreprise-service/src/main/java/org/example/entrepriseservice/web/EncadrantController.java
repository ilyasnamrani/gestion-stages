package org.example.entrepriseservice.web;

import org.example.entrepriseservice.dtos.EncadrantRequest;
import org.example.entrepriseservice.dtos.EncadrantResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.dtos.OffreStageResponse;
import org.example.entrepriseservice.services.EncadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encadrant")
public class EncadrantController {
    @Autowired
    private EncadrantService encadrantService;

    @GetMapping("/all")
    public List<EncadrantResponse> getAllEncadrant() {
        return encadrantService.getAllEncadrants();
    }

    @GetMapping("/{id}")
    public EncadrantResponse getEncadrantById(@PathVariable Long id) {
        return encadrantService.getEncadrant(id);
    }

    @GetMapping("/{id}/offreStages")
    public List<OffreStageResponse> getOffreStageById(@PathVariable Long id) {
        return encadrantService.getOffresStagesEncadrant(id);
    }

    @GetMapping("/{id}/entreprise")
    public EntrepriseResponseV2 getEntrepriseById(@PathVariable Long id) {
        return encadrantService.getEntreprise(id);
    }

    @PostMapping("/create")
    public EncadrantResponse createEncadrant(@RequestBody EncadrantRequest encadrantRequest) {
        return encadrantService.create(encadrantRequest);
    }

    @PutMapping("/{id}/update")
    public EncadrantResponse updateEncadrant(@PathVariable Long id, @RequestBody EncadrantRequest encadrantRequest) {
        return encadrantService.update(encadrantRequest, id);
    }

    @DeleteMapping("/{id}/delete")
    public void  deleteEncadrant(@PathVariable Long id) {
        encadrantService.delete(id);
    }





}