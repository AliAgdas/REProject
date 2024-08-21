package org.example.reproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/factories")
@RequiredArgsConstructor
public class FactoriesController {

    private final FactoriesService factoriesService;
    private final FactoriesCompaniesService factoriesCompaniesService;

    @PostMapping("/add")
    public ResponseEntity<?> addFactories(@RequestBody Factories factories) {
        if(factoriesCompaniesService.isCompanyExistCountry(factories)) {
            Factories savedFactories = factoriesService.addFactories(factories);
            return ResponseEntity.ok(savedFactories);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("there is no company in the country where you want to add factory.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Factories> deleteFactories(int id) {
        Factories deleteFactory = factoriesService.getFactories(id);
        factoriesService.deleteFactories(id);
        return ResponseEntity.ok(deleteFactory);
    }

    @GetMapping("/get")
    public ResponseEntity<Factories> getFactories(@RequestParam int id) {
        Factories factories = factoriesService.getFactoriesRedis(id);
        return ResponseEntity.ok(factories);
    }
}
