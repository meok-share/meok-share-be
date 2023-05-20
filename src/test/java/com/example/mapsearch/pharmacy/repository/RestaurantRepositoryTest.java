package com.example.mapsearch.pharmacy.repository;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.restaurant.entity.Restaurant;
import com.example.mapsearch.restaurant.repository.RestaurantRepository;
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
    void testSavePharmacy() {
        // Given
        Restaurant pharmacy = Restaurant.builder()
                .name("Test Pharmacy")
                .address("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        restaurantRepository.save(pharmacy);

        // Then
        Restaurant savedPharmacy = restaurantRepository.findById(pharmacy.getId()).orElse(null);
        assertThat(savedPharmacy.getName()).isEqualTo("Test Pharmacy");
    }


    @Test
    void TestSaveAll() {
        // Given
        Restaurant restaurant1 = Restaurant.builder()
                .name("Test Restaurant 1")
                .address("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Restaurant restaurant2 = Restaurant.builder()
                .name("Test Restaurant 2")
                .address("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Restaurant restaurant3 = Restaurant.builder()
                .name("Test Restaurant 3")
                .address("123 Main St")
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
