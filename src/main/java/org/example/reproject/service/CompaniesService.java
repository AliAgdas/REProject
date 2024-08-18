package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.reproject.dto.CompaniesDto;
import org.example.reproject.mapper.CompaniesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.example.reproject.repository.CompaniesRepository;
import org.example.reproject.entity.Companies;

@Service
public class CompaniesService {
    private final RedisService redisService;
    private final CompaniesRepository companiesRepository;
    private static final Logger logger = LoggerFactory.getLogger(CompaniesService.class);

    public CompaniesService(RedisService redisService, CompaniesRepository companiesRepository) {
        this.redisService = redisService;
        this.companiesRepository = companiesRepository;
    }

    public Companies addCompanies(Companies companies) {
        return companiesRepository.save(companies);
    }

    public CompaniesDto addCompaniesDtoRedis(CompaniesDto companiesDto) {
        Companies companies = CompaniesMapper.toEntity(companiesDto);
        Companies savedCompany = companiesRepository.save(companies);
        try {
            redisService.saveRedis("company:" + savedCompany.getId(), savedCompany);
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
        }
        return CompaniesMapper.toDTO(savedCompany);
    }

    public CompaniesDto addCompaniesDto(CompaniesDto companiesDto) {
        Companies companies = CompaniesMapper.toEntity(companiesDto);
        Companies savedCompany = companiesRepository.save(companies);
        return CompaniesMapper.toDTO(savedCompany);
    }

    public void deleteCompanies(int id) {
        companiesRepository.deleteById(id);
        redisService.deleteRedis("company:" + id);
    }

    public Companies getCompaniesRedis(int id) {
        try {
            Companies company = redisService.findRedis("company:" + id, Companies.class);
            if (company == null) {
                company= companiesRepository.findById(id).orElse(null);
                if (company != null) {
                    redisService.saveRedis("company:" + id, company);
                }
                logger.info("veri redise yeni kaydedildi");
            }
            else{logger.info("veri redis den geldi");  }

            return company;
        } catch (JsonProcessingException e) {
            logger.error("redis patladı", e);
            return null;
        }
    }

    public Companies getCompanies(int id) {
        return companiesRepository.findById(id).get();
    }


    public Companies updateCompanies(Companies companies) {
        return companiesRepository.save(companies);
    }

}
