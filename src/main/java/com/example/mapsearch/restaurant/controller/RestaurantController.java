package com.example.mapsearch.restaurant.controller;

import com.example.mapsearch.restaurant.cache.RedisTemplateService;
import com.example.mapsearch.restaurant.controller.request.DirectionReq;
import com.example.mapsearch.restaurant.controller.response.DirectionRes;
import com.example.mapsearch.restaurant.dto.RestaurantDto;
import com.example.mapsearch.restaurant.entity.Restaurant;
import com.example.mapsearch.restaurant.service.RestaurantRecommendationService;
import com.example.mapsearch.restaurant.service.RestaurantRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepositoryService restaurantRepositoryService;

    private final RestaurantRecommendationService recommendRestaurantService;

    private final RedisTemplateService redisTemplateService;


    @GetMapping("/recommend")
    public List<DirectionRes> getRecommendRestaurantList(@ModelAttribute DirectionReq req) {
        return recommendRestaurantService.recommendRestaurant(req.getAddress());
    }

    @GetMapping("/list")
    public List<Restaurant> getRestaurantList() {
        return restaurantRepositoryService.findAll();
    }

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {

        List<RestaurantDto> restaurantDtoList = restaurantRepositoryService.findAll()
                .stream()
                .map(restaurant -> RestaurantDto.builder()
                        .id(restaurant.getId())
                        .restaurantName(restaurant.getName())
                        .restaurantAddress(restaurant.getAddress())
                        .latitude(restaurant.getLatitude())
                        .longitude(restaurant.getLongitude())
                        .build()
                ).collect(Collectors.toList());

        restaurantDtoList.forEach(redisTemplateService::save);

        return "success";
    }

}
