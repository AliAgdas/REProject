package org.example.reproject.service;

import org.example.reproject.entity.Companies;
import org.example.reproject.entity.Countries;
import org.example.reproject.entity.Factories;
import org.springframework.stereotype.Service;
import org.example.reproject.service.FactoriesService;
import org.example.reproject.service.CompaniesService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactoriesCompaniesService {

    private final CompaniesService companiesService;
    private final FactoriesService factoriesService;
    public FactoriesCompaniesService(CompaniesService companiesService, FactoriesService factoriesService) {
        this.companiesService = companiesService;
        this.factoriesService = factoriesService;
    }

    public boolean isCompanyExistCountry(Factories factories) {
        Companies company = companiesService.getCompanies(factories.getCompany_factories().getId());
        List<Integer> idList = new ArrayList<>();
        for(Countries countries : company.getCompanies_countries()) {
            idList.add(countries.getId());
        }
        if(idList.contains(factories.getCountry_factories().getId())){
            return true;
        }
        else{
            return false;
        }
    }

}
