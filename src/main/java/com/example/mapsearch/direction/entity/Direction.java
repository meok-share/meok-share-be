package com.example.mapsearch.direction.entity;

import com.example.mapsearch.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.example.mapsearch.common.constants.URL.KakaoUrl.DIRECTION_BASE_URL;
import static com.example.mapsearch.common.constants.URL.KakaoUrl.ROAD_VIEW_BASE_URL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객의 주소 정보
    private String inputAddress;

    private double inputLatitude;

    private double inputLongitude;

    // 약국의 주소 정보
    private String targetPharmacyName;

    private String targetAddress;

    private double targetLatitude;

    private double targetLongitude;

    // 고객 주소 와 약국 주소 사이의 거리
    private double distance;

    public String roadViewUrl() {
        return ROAD_VIEW_BASE_URL + targetLatitude + "," + targetLongitude;
    }

    public String distanceUrlParam() {
        return DIRECTION_BASE_URL + String.join(","
                , targetPharmacyName
                , String.valueOf(targetLatitude)
                , String.valueOf(targetLongitude)
        );
    }
}
