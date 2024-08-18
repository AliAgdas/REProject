package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.example.reproject.repository.CountriesRepository;
import org.example.reproject.entity.Countries;

@Service
public class CountriesService {
    private final RedisService redisService;
    private final CountriesRepository countriesRepository;
    private static final Logger logger = LoggerFactory.getLogger(CountriesService.class);

    public CountriesService(RedisService redisService, CountriesRepository countriesRepository) {
        this.redisService = redisService;
        this.countriesRepository = countriesRepository;
    }

    public Countries addCountriesRedis(Countries countries) {
        Countries savedCountry = countriesRepository.save(countries);
        try {
            redisService.saveRedis("company:" + savedCountry.getId(), savedCountry);
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
        }
        return savedCountry;
    }

    public Countries addCountries(Countries countries) {
        return countriesRepository.save(countries);
    }

    public void deleteCountries(int id) {
        countriesRepository.deleteById(id);
        redisService.deleteRedis("country:" + id);
    }

    public Countries getCountriesRedis(int id) {
        try {
            Countries country = redisService.findRedis("country:" + id, Countries.class);
            if (country == null) {
                country = countriesRepository.findById(id).orElse(null);
                if (country != null) {
                    redisService.saveRedis("country:" + id, country);
                }
                logger.info("veri redise yeni kaydedildi");
            }
            else{logger.info("veri redis den geldi");  }

            return country;
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
            return null;
        }
    }

    public Countries getCountries(int id) {
        return countriesRepository.findById(id).get();
    }

    public Countries updateCountries(Countries countries) {
        return countriesRepository.save(countries);
    }
}
