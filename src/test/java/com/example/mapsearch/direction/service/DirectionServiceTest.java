package com.example.mapsearch.direction.service;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.restaurant.dto.RestaurantDto;
import com.example.mapsearch.restaurant.service.RestaurantSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class DirectionServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private RestaurantSearchService restaurantSearchService;

    private List<RestaurantDto> pharmacyList;

    @BeforeEach
    void setup() {
        pharmacyList = new ArrayList<>();

        pharmacyList.addAll(
                List.of(
                        RestaurantDto.builder()
                                .id(1L)
                                .restaurantName("돌곶이온누리약국")
                                .restaurantAddress("주소1")
                                .latitude(37.61040424)
                                .longitude(127.0569046)
                                .build(),
                        RestaurantDto.builder()
                                .id(2L)
                                .restaurantName("호수온누리약국")
                                .restaurantAddress("주소2")
                                .latitude(37.60894036)
                                .longitude(127.029052)
                                .build()
                )
        );
    }

    @Test
    void test() {

    }

}