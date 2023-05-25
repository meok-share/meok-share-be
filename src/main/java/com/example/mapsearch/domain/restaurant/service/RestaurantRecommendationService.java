package com.example.mapsearch.domain.restaurant.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import com.example.mapsearch.domain.direction.entity.Direction;
import com.example.mapsearch.domain.direction.service.Base62Service;
import com.example.mapsearch.domain.direction.service.DirectionService;
import com.example.mapsearch.domain.restaurant.controller.response.DirectionRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final DirectionService directionService;

    private final Base62Service base62Service;

    @Value("${map.recommendation.base.url}")
    private String baseUrl;

    /**
     * 고객의 주소를 받아서 카카오 주소 api 를 이용하여 가장 가까운 음식점을 추천해준다.
     *
     * @param address
     * @return
     */
    public List<DirectionRes> recommendRestaurant(String address) {

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponse) || CollectionUtils.isEmpty(kakaoApiResponse.getDocumentList())) {
            log.error("kakao api response is null or document list is empty");
            return Collections.emptyList();
        }

        final Document document = kakaoApiResponse
                .getDocumentList()
                .get(0);

        // kakao 카테고리를 이용한 장소 검색 api 허용
        final List<Direction> directionList = directionService.buildDirectionListByCategoryApi(document);

        return directionService
                .saveAll(directionList)
                .stream()
                .map(direction -> DirectionRes.of(direction, baseUrl, base62Service))
                .collect(Collectors.toList());
    }

}
