package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.reproject.entity.Factories;
import org.example.reproject.repository.FactoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoriesService {

    private final RedisService redisService;
    private final FactoriesRepository factoriesRepository;
    private static final Logger logger = LoggerFactory.getLogger(FactoriesService.class);

    @Autowired
    public FactoriesService(RedisService redisService, FactoriesRepository factoriesRepository) {
        this.redisService = redisService;
        this.factoriesRepository = factoriesRepository;
    }

    public Factories addFactoriesRedis(Factories factories) {
        Factories savedFactory = factoriesRepository.save(factories);
        try {
            redisService.saveRedis("factory:" + savedFactory.getId(), savedFactory);
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
        }
        return savedFactory;
    }

    public Factories addFactories(Factories factories) {
        return factoriesRepository.save(factories);
    }

    public void deleteFactories(int id) {
        factoriesRepository.deleteById(id);
        redisService.deleteRedis("factory:" + id);
    }

    public Factories getFactoriesRedis(int id) {
        try {
            Factories factory = redisService.findRedis("factory:" + id, Factories.class);
            if (factory == null) {
                factory = factoriesRepository.findById(id).orElse(null);
                if (factory != null) {
                    redisService.saveRedis("factory:" + id, factory);
                    logger.info("veri redise yeni kaydedildi");
                }
                else{logger.error("böyle bir veri yok");}
            }
            else{logger.info("veri redis den geldi");  }

            return factory;
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
            return null;
        }
    }

    public Factories getFactories(int id){
        return factoriesRepository.findById(id).get();
    }
}
