package com.example.mapsearch.restaurant.repository;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.domain.restaurant.entity.Restaurant;
import com.example.mapsearch.domain.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RestaurantRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void setup() {
        restaurantRepository.deleteAll();
    }

    @Test
    void testSaveRestaurant() {
        // Given
        Restaurant restaurant = Restaurant.builder()
                .restaurantName("Test Restaurant")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        restaurantRepository.save(restaurant);

        // Then
        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).orElse(null);
        assertThat(savedRestaurant.getRestaurantName()).isEqualTo("Test Restaurant");
    }


    @Test
    void TestSaveAll() {
        // Given
        Restaurant restaurant1 = Restaurant.builder()
                .restaurantName("Test Restaurant 1")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .restaurantName("Test Restaurant 2")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .restaurantName("Test Restaurant 3")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));

        // Then
        List<Restaurant> savedPharmacies = restaurantRepository.findAll();
        assertThat(savedPharmacies.size()).isEqualTo(3);
    }
}
