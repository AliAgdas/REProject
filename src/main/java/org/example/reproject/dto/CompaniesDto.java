package org.example.reproject.dto;

import java.util.List;
import lombok.Data;
import org.example.reproject.entity.Countries;

@Data
public class CompaniesDto {
    private String name;
    private int totalRecycleEarningUsd;
    private List<Countries> companies_countries;

}
