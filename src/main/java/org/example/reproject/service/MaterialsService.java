package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.example.reproject.repository.MaterialsRepository;
import org.example.reproject.entity.Materials;

@Service
public class MaterialsService {
    private final RedisService redisService;
    private final MaterialsRepository materialsRepository;
    private static final Logger logger = LoggerFactory.getLogger(MaterialsService.class);

    public MaterialsService(RedisService redisService, MaterialsRepository materialsRepository) {
        this.redisService = redisService;
        this.materialsRepository = materialsRepository;
    }

    public Materials addMaterialsRedis(Materials materials) {
        Materials savedMaterial= materialsRepository.save(materials);
        try {
            redisService.saveRedis("material:" + savedMaterial.getId(), savedMaterial);
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
        }
        return savedMaterial;
    }

    public Materials addMaterials(Materials materials){
        return materialsRepository.save(materials);
    }

    public void deleteMaterials(int id) {
        materialsRepository.deleteById(id);
        redisService.deleteRedis("material:" + id);
    }

    public Materials getMaterialsRedis(int id) {
        try {
            Materials material = redisService.findRedis("material:" + id, Materials.class);
            if (material == null) {
                material = materialsRepository.findById(id).orElse(null);
                if (material != null) {
                    redisService.saveRedis("material:" + id, material);
                    logger.info("veri redise yeni kaydedildi");
                }
                else{logger.error("böyle bir veri yok");}
            }
            else{logger.info("veri redis den geldi");  }

            return material;
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
            return null;
        }
    }

    public Materials getMaterials(int id){
        return materialsRepository.findById(id).get();
    }
}
