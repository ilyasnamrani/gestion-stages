package org.example.entrepriseservice.web;

import org.example.entrepriseservice.dtos.EntrepriseRequest;
import org.example.entrepriseservice.dtos.EntrepriseResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entreprise")
public class EntrepriseController {
    @Autowired
    private EntrepriseService entrepriseService;

    @GetMapping("/v1/all")
    public List<EntrepriseResponse> getAllEntreprisesV1(){
        return entrepriseService.getAllEntreprisesV1();
    }

    @GetMapping("/v2/all")
    public List<EntrepriseResponseV2> getAllEntreprisesV2(){
        return entrepriseService.getAllEntreprisesV2();
    }

    @GetMapping("/{id}/v1")
    public EntrepriseResponse getEntrepriseV1(@PathVariable Long id){
        return entrepriseService.getEntrepriseV1(id);
    }

    @GetMapping("/{id}/v2")
    public EntrepriseResponseV2 getEntrepriseV2(@PathVariable Long id){
        return entrepriseService.getEntrepriseV2(id);
    }

    @PostMapping("/create")
    public EntrepriseResponse createEntreprise(@RequestBody EntrepriseRequest entrepriseRequest){
        return entrepriseService.createEntreprise(entrepriseRequest);
    }

    @PutMapping("/{id}/update")
    public EntrepriseResponseV2 updateEntreprise(@RequestBody EntrepriseRequest entrepriseRequest, @PathVariable Long id){
        return entrepriseService.updateEntreprise(id, entrepriseRequest);
    }



    @DeleteMapping("/{id}/delete")
    public void deleteEntreprise(@PathVariable Long id){
        entrepriseService.deleteEntreprise(id);
    }

}