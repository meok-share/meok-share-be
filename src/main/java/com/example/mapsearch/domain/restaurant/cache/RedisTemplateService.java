package com.example.mapsearch.domain.restaurant.cache;

import com.example.mapsearch.domain.restaurant.dto.RestaurantDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTemplateService {

    private static final String CACHE_KEY = "RESTAURANT";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(RestaurantDto dto) {
        if (ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getId())) {
            log.error("required values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, dto.getId().toString(), serializeRestaurant(dto));
            log.info("save restaurant: {}", dto);
        } catch (Exception e) {
            log.error("failed to save restaurant: {}", e.getMessage());
        }
    }

    public List<RestaurantDto> findAll() {
        try {
            Map<String, String> entries = hashOperations.entries(CACHE_KEY);
            List<RestaurantDto> list = new ArrayList<>();

            for (String s : entries.values()) {
                RestaurantDto restaurantDto = deserializeRestaurant(s);
                list.add(restaurantDto);
            }

            return list;
        } catch (Exception e) {
            log.error("Failed to find all restaurants: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("delete restaurant: {}", id);
    }

    private String serializeRestaurant(RestaurantDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    private RestaurantDto deserializeRestaurant(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, RestaurantDto.class);
    }

}
