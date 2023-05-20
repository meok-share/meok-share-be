package com.example.mapsearch.restaurant.cache;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.restaurant.dto.RestaurantDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplateService redisTemplateService;

    @BeforeEach
    void setup() {
        // 테스트 시작 전에 모든 데이터를 삭제한다.
        redisTemplateService.findAll().forEach(dto -> {
            redisTemplateService.delete(dto.getId());
        });
    }

    @Test
    @DisplayName("redis 에 해당 음식점의 저장이 성공적으로 되어야한다.")
    void testSave() {
        // Given
        RestaurantDto dto = RestaurantDto.builder()
                .id(1L)
                .restaurantName("Test Restaurant")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        redisTemplateService.save(dto);

        // Then
        final List<RestaurantDto> restaurantDtoList = redisTemplateService.findAll();

        assertAll(
                () -> assertEquals(1, restaurantDtoList.size()),
                () -> assertEquals(dto.getId(), restaurantDtoList.get(0).getId()),
                () -> assertEquals(dto.getRestaurantName(), restaurantDtoList.get(0).getRestaurantName()),
                () -> assertEquals(dto.getRestaurantAddress(), restaurantDtoList.get(0).getRestaurantAddress()),
                () -> assertEquals(dto.getLatitude(), restaurantDtoList.get(0).getLatitude()),
                () -> assertEquals(dto.getLongitude(), restaurantDtoList.get(0).getLongitude())
        );
    }

    @Test
    @DisplayName("redis 에 저장시, dto 혹은 id 값이 비어있을경우 예외 처리 테스트")
    void invalidSaveTest() {

        RestaurantDto dto = RestaurantDto.builder()
                .restaurantName("Test Restaurant")
                .restaurantAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        redisTemplateService.save(dto);

        assertEquals(0, redisTemplateService.findAll().size());
    }

}