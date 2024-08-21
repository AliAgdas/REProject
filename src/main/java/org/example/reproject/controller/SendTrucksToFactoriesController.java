package org.example.reproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.reproject.entity.*;
import org.example.reproject.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendTruckToFactories")
@RequiredArgsConstructor
public class SendTrucksToFactoriesController {

    private final SendTrucksToFactoriesService sendTrucksToFactoriesService;
    private final TrucksService trucksService;

    @GetMapping("/sendTruck")
    public ResponseEntity<?>sendTruck(@RequestParam int factoryId, @RequestParam int kg,@RequestParam int materialId) throws JsonProcessingException {
        Factories factory = sendTrucksToFactoriesService.getFactories(factoryId);
        Materials material = sendTrucksToFactoriesService.getMaterial(materialId);
        sendTrucksToFactoriesService.setRequiredKg(kg);
        if(sendTrucksToFactoriesService.isListEmpty(factory)) {
            if (sendTrucksToFactoriesService.processAndSendTruckData(factory)) {
                if (sendTrucksToFactoriesService.isMaterialExists(factory, materialId)) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    sendTrucksToFactoriesService.updateFactoriesAndTrucks(factory, material);
                    return ResponseEntity.ok(factory);
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("selected material is not in factory's material list.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("a truck has already been assigned to this factory.");
            }
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("there is no truck available");
        }
    }

    @GetMapping("/unloadTruck")
    public ResponseEntity<?> unloadTruck(@RequestParam int truckId ) {

        if(sendTrucksToFactoriesService.unloadTruck(truckId)){
            Trucks unloadedTruck = trucksService.getTruck(truckId);
            return ResponseEntity.ok(unloadedTruck);
        }
        else
        {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("this truck already unloaded.");}
    }
}
