package org.example.reproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.reproject.dto.*;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompaniesController {

    private final CompaniesService companiesService;

    @PostMapping("/add")
    public ResponseEntity<CompaniesDto> addCompanies(@RequestBody CompaniesDto companiesDto) {
        CompaniesDto savedCompanies = companiesService.addCompaniesDto(companiesDto);
        return ResponseEntity.ok(savedCompanies);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Companies> deleteCompanies(@RequestParam int id) {
        Companies companies = companiesService.getCompanies(id);
        companiesService.deleteCompanies(id);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/get")
    public ResponseEntity<Companies> getCompanies(@RequestParam int id) {
        Companies companies = companiesService.getCompanies(id);
        return ResponseEntity.ok(companies);
    }

}
