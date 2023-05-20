package com.example.mapsearch.restaurant.service;


import com.example.mapsearch.restaurant.cache.RedisTemplateService;
import com.example.mapsearch.restaurant.dto.RestaurantDto;
import com.example.mapsearch.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantSearchService {

    private final RestaurantRepositoryService restaurantRepositoryService;

    private final RedisTemplateService redisTemplateService;

    public List<RestaurantDto> searchPharmacyList() {

        // redis
        final List<RestaurantDto> restaurantList = redisTemplateService.findAll();

        if (!restaurantList.isEmpty()) {
            return restaurantList;
        }

        // db
        final List<Restaurant> pharmacyList = restaurantRepositoryService.findAll();

        return pharmacyList.stream()
                .map(RestaurantDto::of)
                .collect(Collectors.toList());
    }

}
