package org.example.reproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.reproject.dto.*;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialsController {

    private final MaterialsService materialsService;

    @PostMapping("/add")
    public ResponseEntity<Materials> addMaterials(@RequestBody Materials materials) {
        Materials savedMaterial = materialsService.addMaterialsRedis(materials);
        return ResponseEntity.ok(savedMaterial);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Materials> deleteMaterials(@RequestParam int id) {
        Materials deleteMaterials = materialsService.getMaterials(id);
        materialsService.deleteMaterials(id);
        return ResponseEntity.ok(deleteMaterials);
    }

    @GetMapping("/get")
    public ResponseEntity<Materials> getMaterials(@RequestParam int id) {
        Materials materials = materialsService.getMaterialsRedis(id);
        return ResponseEntity.ok(materials);
    }

}
