package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.example.reproject.entity.*;
import org.springframework.stereotype.Service;
import org.example.reproject.producer.TrucksProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendTrucksToFactoriesService {
    private final RedisService redisService;
    @Getter
    private double requiredKg;
    @Setter
    private static Trucks choosenTruck;

    private final TrucksProducer trucksProducer;
    private final TrucksService trucksService;
    private final FactoriesService factoriesService;
    private final MaterialsService materialsService;

    public SendTrucksToFactoriesService(TrucksProducer trucksProducer, TrucksService trucksService, FactoriesService factoriesService, MaterialsService materialsService, RedisService redisService) {
        this.trucksProducer = trucksProducer;
        this.trucksService = trucksService;
        this.factoriesService = factoriesService;
        this.materialsService = materialsService;
        this.redisService = redisService;
    }

    public Factories getFactories(int id) {
        return factoriesService.getFactories(id);
    }

    public Materials getMaterial(int id){
        return materialsService.getMaterials(id);
    }

    public void setRequiredKg(int kg){
        this.requiredKg = kg;
    }

    public Trucks getChoosenTruck(){
        return this.choosenTruck;
    }

    public Factories updateFactoriesAndTrucks(Factories factories, Materials material) throws JsonProcessingException {
        Trucks newTruck = trucksService.getTruck(getChoosenTruck().getId());
        newTruck.setFull(true);
        newTruck.setFactory(factories);
        setChoosenTruck(newTruck);
        trucksService.addTruckRedis(newTruck);

        Factories newFactory = factoriesService.getFactories(factories.getId());
        newFactory.setTruck(getChoosenTruck());
        newFactory.setNowRecycleUsd(recycleBenefitCalculation(factories,material));
        waitTruck();
        return factoriesService.addFactoriesRedis(newFactory);
    }

    public Trucks waitTruck() throws JsonProcessingException {
        Trucks waitingTruck = this.getChoosenTruck();
        trucksService.lockTruck(this.getChoosenTruck());
        return waitingTruck;
    }

    private double recycleBenefitCalculation(Factories factories, Materials material) {
        double materialRecycleBenefit = material.getPurchaseCostUsd()- material.getRecyclePurchaseCostUsd();
        double totalPurchaseBenefit = materialRecycleBenefit*this.requiredKg;
        double finalBenefit;
        if(factories.getCountry_factories().getMinKgForBonus()<= this.requiredKg){
            finalBenefit = totalPurchaseBenefit+factories.getCountry_factories().getRecycleBonusUsd()-(factories.getDistanceKm()*3);
            return finalBenefit;
        }
        else{
            finalBenefit = totalPurchaseBenefit-(factories.getDistanceKm()*3);
            return finalBenefit;
        }
    }

    public boolean isListEmpty(Factories factories){
        List<Trucks> trucksList = trucksService.findByIsFullFalseAndCountry(factories.getCountry_factories(),this.requiredKg);
        trucksList.stream().filter(truck -> !redisService.isLockedRedis("lockedTruck:"+truck.getId())).collect(Collectors.toList());
        if(!trucksList.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean processAndSendTruckData(Factories factories) {
        if(factories.getTruck()==null)
        {
            List<Trucks> trucksToSend = trucksService.findByIsFullFalseAndCountry(factories.getCountry_factories(),this.requiredKg);
            trucksProducer.sendTrucksData(trucksToSend.stream().filter(truck -> !redisService.isLockedRedis("lockedTruck:"+truck.getId())).collect(Collectors.toList()), getRequiredKg());
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isMaterialExists(Factories factories, int id) {
        List<Integer> idList = new ArrayList<>();
        for(Materials materials : factories.getMaterials_factories()){
            idList.add(materials.getId());
        }
        if(idList.contains(id)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean unloadTruck(int truckId) {
        Trucks unloadTruck = trucksService.getTruck(truckId);
        if(unloadTruck.isFull()){
            Factories unloadedFactory = unloadTruck.getFactory();
            Companies company = unloadedFactory.getCompany_factories();
            company.setTotalRecycleEarningUsd(company.getTotalRecycleEarningUsd()+unloadedFactory.getNowRecycleUsd());
            unloadedFactory.setTruck(null);
            unloadedFactory.setNowRecycleUsd(0.0);
            unloadTruck.setFull(false);
            unloadTruck.setFactory(null);

            factoriesService.addFactoriesRedis(unloadedFactory);
            trucksService.addTruckRedis(unloadTruck);
            return true;
        }
        else{
            return false;
        }
    }
}