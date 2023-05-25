package com.example.mapsearch.domain.restaurant.controller;

import com.example.mapsearch.domain.restaurant.cache.RedisTemplateService;
import com.example.mapsearch.domain.restaurant.dto.RestaurantDto;
import com.example.mapsearch.domain.restaurant.entity.Restaurant;
import com.example.mapsearch.domain.restaurant.service.RestaurantRecommendationService;
import com.example.mapsearch.domain.restaurant.service.RestaurantRepositoryService;
import com.example.mapsearch.domain.restaurant.controller.request.DirectionReq;
import com.example.mapsearch.domain.restaurant.controller.response.DirectionRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@Controller
@RestController
@RequestMapping("/location")
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
                        .restaurantName(restaurant.getRestaurantName())
                        .restaurantAddress(restaurant.getRestaurantAddress())
                        .latitude(restaurant.getLatitude())
                        .longitude(restaurant.getLongitude())
                        .build()
                ).collect(Collectors.toList());

        restaurantDtoList.forEach(redisTemplateService::save);

        return "success";
    }


    @Operation(summary = "문자열 반복", description = "파라미터로 받은 문자열을 2번 반복합니다.")
    @Parameter(name = "str", description = "2번 반복할 문자열")
    @GetMapping("/returnStr")
    public String returnStr(@RequestParam String str) {
        return str + "\n" + str;
    }

}
