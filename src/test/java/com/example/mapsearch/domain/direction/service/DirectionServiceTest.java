package com.example.mapsearch.domain.direction.service;

import com.example.mapsearch.integration.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.domain.restaurant.dto.RestaurantDto;
import com.example.mapsearch.domain.restaurant.service.RestaurantSearchService;
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

    private List<RestaurantDto> restaurantList;

    @BeforeEach
    void setup() {
        restaurantList = new ArrayList<>();

        restaurantList.addAll(
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