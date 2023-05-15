package com.example.mapsearch.pharmacy.controller.response;


import com.example.mapsearch.direction.entity.Direction;
import com.example.mapsearch.direction.service.Base62Service;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Getter
@Builder
public class DirectionRes {

    private String pharmacyName; // 약국명

    private String pharmacyAddress; // 약국 주소

    private String directionUrl; // 길안내 url

    private String roadViewUrl; // 로드뷰 url

    private String distance; // 고객 주소와 약국 주소와 의 거리

    public static DirectionRes of(Direction directions, final String baseUrl, Base62Service base62Service) {

        // 한글의 부분 인코딩이 필요한 경우 사용
        final String uriString = UriComponentsBuilder
                .fromHttpUrl(directions.distanceUrlParam())
                .toUriString();

//        String encodedId = uriString + baseUrl + base62Service.encodeDirectionId(directions.getId());
        log.info("direction url: {}", directions.distanceUrlParam());
        log.info("direction params : {}", directions.roadViewUrl());

        return DirectionRes.builder()
                .pharmacyName(directions.getTargetPharmacyName())
                .pharmacyAddress(directions.getTargetAddress())
                .directionUrl(uriString)
                .roadViewUrl(directions.roadViewUrl())
                .distance(String.format("%.2f km", directions.getDistance()))
                .build();
    }

}
