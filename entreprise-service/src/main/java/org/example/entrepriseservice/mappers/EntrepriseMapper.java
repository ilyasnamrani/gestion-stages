package org.example.entrepriseservice.mappers;

import org.example.entrepriseservice.dtos.EntrepriseRequest;
import org.example.entrepriseservice.dtos.EntrepriseResponse;
import org.example.entrepriseservice.dtos.EntrepriseResponseV2;
import org.example.entrepriseservice.entities.Entreprise;

public interface EntrepriseMapper {
    public Entreprise toEntreprise(EntrepriseRequest entrepriseRequest);
    public EntrepriseResponse toEntrepriseResponse(Entreprise entreprise);
    public EntrepriseResponseV2 toEntrepriseResponseV2(Entreprise entreprise);
}
