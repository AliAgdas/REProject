package org.example.reproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.reproject.dto.*;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TrucksController {

    private final TrucksService trucksService;

    @PostMapping("/add")
    public ResponseEntity<Trucks> addTruck(@RequestBody Trucks trucks) {
         Trucks savedTruck = trucksService.addTruck(trucks);
         return ResponseEntity.ok(savedTruck);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Trucks> deleteTruck(@RequestParam int id) {
        Trucks deleteTruck = trucksService.getTruck(id);
        trucksService.deleteTruck(id);
        return ResponseEntity.ok(deleteTruck);
    }

    @GetMapping("/get")
    public ResponseEntity<Trucks> getTruck(@RequestParam int id) {
        Trucks truck = trucksService.getTruck(id);
        return ResponseEntity.ok(truck);
    }
}
