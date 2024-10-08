package org.example.reproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void saveRedis(String key, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, jsonValue);
    }

    public <T> T findRedis(String key, Class<T> clazz) throws JsonProcessingException {
        String jsonValue = redisTemplate.opsForValue().get(key);
        if (jsonValue != null) {
            return objectMapper.readValue(jsonValue, clazz);
        }
        return null;
    }

    public void deleteRedis(String key) {
        redisTemplate.delete(key);
    }

    public boolean lockRedis(String key, Object value) throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(value);
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(key, jsonValue, 300, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(isLocked);
    }

    public void unlockRedis(String key) {
        redisTemplate.delete(key);
    }

    public boolean isLockedRedis(String key) {
        return redisTemplate.hasKey(key);
    }
}



