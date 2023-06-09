package com.example.mapsearch.domain.direction.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.service.KakaoCategorySearchService;
import com.example.mapsearch.domain.direction.entity.Direction;
import com.example.mapsearch.domain.direction.repository.DirectionRepository;
import com.example.mapsearch.domain.restaurant.service.RestaurantSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    // 최대 검색 갯수
    private static final int MAX_SEARCH_COUNT = 3;

    // 반경 10km 이내
    private static final double RADIUS_KM = 10.0;

    private final RestaurantSearchService restaurantSearchService;

    private final DirectionRepository directionRepository;

    private final KakaoCategorySearchService kakaoCategorySearchService;

    private final Base62Service base62Service;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public String findDirectionUrlById(String encodedId) {
        final Long decodedId = base62Service.decodeDirectionId(encodedId);

        final Direction direction = directionRepository.findById(decodedId).orElse(null);

        return UriComponentsBuilder.fromHttpUrl(direction.distanceUrlParam()).toUriString();
    }

    public List<Direction> buildDirectionList(Document documentDto) {
        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return restaurantSearchService.searchRestaurantList()
                .stream().map(restaurantDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetRestaurantName(restaurantDto.getRestaurantName())
                                .targetAddress(restaurantDto.getRestaurantAddress())
                                .targetLatitude(restaurantDto.getLatitude())
                                .targetLongitude(restaurantDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                restaurantDto.getLatitude(), restaurantDto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula 이용 (거리계산 알고리즘)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371.0; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

    // restaurant search by category kakao api
    public List<Direction> buildDirectionListByCategoryApi(Document inputDocumentDto) {
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestRestaurantCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetRestaurantName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

}
