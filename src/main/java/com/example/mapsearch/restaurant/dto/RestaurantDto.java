package com.example.mapsearch.restaurant.dto;

import com.example.mapsearch.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;

    private String restaurantName;

    private String restaurantAddress;

    private double latitude;

    private double longitude;

    public static RestaurantDto of(Restaurant restaurant) {
        return RestaurantDto.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getName())
                .restaurantAddress(restaurant.getAddress())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .build();
    }
}