package org.example.reproject.service;

import lombok.Getter;
import lombok.Setter;
import org.example.reproject.entity.*;
import org.springframework.stereotype.Service;
import org.example.reproject.producer.TrucksProducer;

import java.util.ArrayList;
import java.util.List;

@Service
public class SendTrucksToFactoriesService {
    private final CompaniesService companiesService;
    @Getter
    private double requiredKg;

    @Setter
    private static Trucks choosenTruck;

    private final TrucksProducer trucksProducer;
    private final TrucksService trucksService;
    private final FactoriesService factoriesService;
    private final MaterialsService materialsService;

    public SendTrucksToFactoriesService(TrucksProducer trucksProducer, TrucksService trucksService, FactoriesService factoriesService, MaterialsService materialsService, CompaniesService companiesService) {
        this.trucksProducer = trucksProducer;
        this.trucksService = trucksService;
        this.factoriesService = factoriesService;
        this.materialsService = materialsService;
        this.companiesService = companiesService;
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

    public Trucks getChoosenTruckEntity(){
        return this.choosenTruck;
    }

    public Factories updateFactoriesAndTrucks(Factories factories, Materials material) {
        Trucks newTruck = trucksService.getTruck(getChoosenTruckEntity().getId());
        newTruck.setFull(true);
        newTruck.setFactory(factories);
        setChoosenTruck(newTruck);
        trucksService.addTruck(newTruck);

        Factories newFactory = factoriesService.getFactories(factories.getId());
        newFactory.setTruck(getChoosenTruckEntity());
        newFactory.setNowRecycleUsd((material.getPurchaseCostUsd()- material.getRecyclePurchaseCostUsd())*this.requiredKg);
        return factoriesService.addFactories(newFactory);
    }

    public boolean isListEmpty(Factories factories){
        List<Trucks> trucksList = trucksService.findByIsFullFalseAndCountry(factories.getCountry_factories(),this.requiredKg);
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
            trucksProducer.sendTrucksData(trucksToSend, getRequiredKg());
            return true;
        }
        else
        {
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
        else{return false;}
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

            companiesService.addCompanies(company);
            factoriesService.addFactories(unloadedFactory);
            trucksService.addTruck(unloadTruck);
            return true;
        }
        else{
            return false;
        }
    }
}