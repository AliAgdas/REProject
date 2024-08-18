package org.example.reproject.mapper;

import org.example.reproject.entity.Companies;
import org.example.reproject.dto.CompaniesDto;
import org.springframework.stereotype.Component;

@Component
public class CompaniesMapper {

    public static CompaniesDto toDTO(Companies companies) {
        CompaniesDto dto = new CompaniesDto();
        dto.setName(companies.getName());
        dto.setCompanies_countries(companies.getCompanies_countries());
        dto.setTotalRecycleEarningUsd(0);
        return dto;
    }

    public static Companies toEntity(CompaniesDto dto) {
        Companies companies = new Companies();
        companies.setName(dto.getName());
        companies.setCompanies_countries(dto.getCompanies_countries());
        companies.setTotalRecycleEarningUsd(0);
        return companies;
    }
}
