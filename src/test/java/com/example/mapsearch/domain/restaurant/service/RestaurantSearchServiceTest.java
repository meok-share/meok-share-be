package com.example.mapsearch.domain.restaurant.service;

import com.example.mapsearch.domain.restaurant.cache.RedisTemplateService;
import com.example.mapsearch.domain.restaurant.dto.RestaurantDto;
import com.example.mapsearch.domain.restaurant.entity.Restaurant;
import com.example.mapsearch.domain.restaurant.service.RestaurantRepositoryService;
import com.example.mapsearch.domain.restaurant.service.RestaurantSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RestaurantSearchServiceTest {

    @InjectMocks
    private RestaurantSearchService restaurantSearchService;

    @Mock
    private RestaurantRepositoryService restaurantRepositoryService;

    @Mock
    private RedisTemplateService redisTemplateService;

    private List<Restaurant> restaurantList;

    @BeforeEach
    void setUp() {
        restaurantSearchService = new RestaurantSearchService(restaurantRepositoryService, redisTemplateService);

        restaurantList = Arrays.asList(
                Restaurant.builder()
                        .id(1L)
                        .restaurantName("맛집1")
                        .restaurantAddress("주소1")
                        .latitude(37.12341234)
                        .longitude(127.12341234)
                        .build(),
                Restaurant.builder()
                        .id(1L)
                        .restaurantName("맛집1")
                        .restaurantAddress("주소1")
                        .latitude(37.12341234)
                        .longitude(127.12341234)
                        .build()
        );
    }

    @DisplayName("레디스 장애시 DB를 이용하여 데이터를 조회한다.")
    @Test
    void test() {
        // when
        when(restaurantRepositoryService.findAll())
                .thenReturn(restaurantList);

        // then
        List<RestaurantDto> restaurantList = restaurantSearchService.searchRestaurantList();
        assertThat(restaurantList).hasSize(2);
    }

}
