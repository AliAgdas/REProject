package org.example.reproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.reproject.dto.*;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountriesController {

    private final CountriesService countriesService;

    @PostMapping("/add")
    public ResponseEntity<Countries> addCountries(@RequestBody Countries countries) {
        Countries savedCountries = countriesService.addCountries(countries);
        return ResponseEntity.ok(savedCountries);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Countries> removeCountries(@RequestParam int id) {
        Countries deleteCountry = countriesService.getCountries(id);
        countriesService.deleteCountries(id);
        return ResponseEntity.ok(deleteCountry);
    }

    @GetMapping("/get")
    public ResponseEntity<Countries> getCountries(@RequestParam int id) {
        Countries country = countriesService.getCountriesRedis(id);
        return ResponseEntity.ok(country);
    }

    @PostMapping("/update")
    public ResponseEntity<Countries> updateCountries(@RequestBody Countries countries) {
        Countries savedCountries = countriesService.updateCountries(countries);
        return ResponseEntity.ok(savedCountries);
    }
}
