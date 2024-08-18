package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.reproject.entity.Countries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.example.reproject.repository.TrucksRepository;
import org.example.reproject.entity.Trucks;

import java.util.List;

@Service
public class TrucksService {
    private final TrucksRepository trucksRepository;
    private final RedisService redisService;
    private static final Logger logger = LoggerFactory.getLogger(TrucksService.class);
    public TrucksService(TrucksRepository trucksRepository, RedisService redisService) {
        this.trucksRepository = trucksRepository;
        this.redisService = redisService;
    }

    public Trucks addTruckRedis(Trucks truck) {
        Trucks savedTruck = trucksRepository.save(truck);
        try {
            redisService.saveRedis("truck:" + savedTruck.getId(), savedTruck);
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
        }
        return savedTruck;
    }

    public Trucks addTruck(Trucks truck) {
        return trucksRepository.save(truck);
    }

    public void deleteTruck(int id) {
        trucksRepository.deleteById(id);
        redisService.deleteRedis("truck:"+id);
    }

    public Trucks getTruckRedis(int id) {
        try {
            Trucks truck = redisService.findRedis("truck:" + id, Trucks.class);
            if (truck == null) {
                truck = trucksRepository.findById(id).orElse(null);
                if (truck != null) {
                    redisService.saveRedis("truck:" + id, truck);
                }
                logger.info("veri redise yeni kaydedildi");
            }
            else{logger.info("veri redis den geldi");  }

            return truck;
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
            return null;
        }
    }

    public Trucks getTruck(int id){
        return trucksRepository.findById(id).get();
    }

    public List<Trucks> findByIsFullFalseAndCountry(Countries country, double requiredKg) {
        return trucksRepository.findByIsFullFalseAndCountry(country,requiredKg);
    }

}
