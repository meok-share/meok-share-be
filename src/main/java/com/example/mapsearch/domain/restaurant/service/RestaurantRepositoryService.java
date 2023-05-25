package com.example.mapsearch.domain.restaurant.service;

import com.example.mapsearch.domain.restaurant.entity.Restaurant;
import com.example.mapsearch.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantRepositoryService {

    private final RestaurantRepository restaurantRepository;

    public void updateAddress(Long id, String address) {
        Restaurant entity = restaurantRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            log.error("[RestaurantRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.updateAddress(address);
    }


    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}
