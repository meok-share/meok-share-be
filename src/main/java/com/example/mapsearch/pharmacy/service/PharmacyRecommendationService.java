package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import com.example.mapsearch.direction.entity.Direction;
import com.example.mapsearch.direction.service.Base62Service;
import com.example.mapsearch.pharmacy.controller.response.DirectionRes;
import com.example.mapsearch.direction.service.DirectionService;
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
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final DirectionService directionService;

    private final Base62Service base62Service;

    @Value("${map.recommendation.base.url}")
    private String baseUrl;

    /**
     * 고객의 주소를 받아서 공공기관 약국 데이터를 이용하여 가장 가까운 약국을 추천해주는 서비스
     * @param address
     * @return
     */
    public List<DirectionRes> recommendPharmacy(String address) {

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponse) || CollectionUtils.isEmpty(kakaoApiResponse.getDocumentList())) {
            log.error("kakao api response is null or document list is empty");
            return Collections.emptyList();
        }

        final Document document = kakaoApiResponse
                .getDocumentList()
                .get(0);

        // 공공기관 약국 데이터 및 거리계산 알고리즘 이용
//         List<Direction> directionList = directionService.buildDirectionList(document);

        // kakao 카테고리를 이용한 장소 검색 api 허용
        final List<Direction> directionList = directionService.buildDirectionListByCategoryApi(document);

        return directionService
                .saveAll(directionList)
                .stream()
                .map(direction -> DirectionRes.of(direction, baseUrl, base62Service))
                .collect(Collectors.toList());
    }

}
