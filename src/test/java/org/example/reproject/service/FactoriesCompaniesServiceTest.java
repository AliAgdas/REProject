package org.example.reproject.service;

import org.example.reproject.entity.Factories;
import org.example.reproject.entity.Countries;
import org.example.reproject.entity.Companies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;


public class FactoriesCompaniesServiceTest {

    @Mock
    private CompaniesService companiesService;

    @InjectMocks
    private FactoriesCompaniesService factoriesCompaniesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsCompanyExistCountry_whenCompanyHasCountry_shouldReturnTrue() {
        Factories factory = new Factories();
        Companies company = new Companies();
        Countries country = new Countries();
        country.setId(1);

        company.setCompanies_countries(Arrays.asList(country));
        factory.setCompany_factories(company);

        factory.setCountry_factories(country);

        given(companiesService.getCompanies(company.getId())).willReturn(company);

        assertTrue(factoriesCompaniesService.isCompanyExistCountry(factory));
    }

    @Test
    void testIsCompanyExistCountry_whenCompanyDoesNotHaveCountry_shouldReturnFalse() {
        Factories factory = new Factories();
        Companies company = new Companies();
        Countries country1 = new Countries();
        Countries country2 = new Countries();
        country1.setId(1);
        country2.setId(2);

        company.setCompanies_countries(Arrays.asList(country1));
        factory.setCompany_factories(company);

        factory.setCountry_factories(country2);

        given(companiesService.getCompanies(company.getId())).willReturn(company);

        assertFalse(factoriesCompaniesService.isCompanyExistCountry(factory));
    }
}
