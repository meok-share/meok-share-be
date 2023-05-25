package com.example.mapsearch.domain.restaurant.repository;

import com.example.mapsearch.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
