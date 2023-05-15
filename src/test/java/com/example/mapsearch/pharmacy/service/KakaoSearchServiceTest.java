package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class KakaoSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService service;

    private static final String 주소 = "서울특별시 강남구 논현로 508";
    @Test
    void testSearchPharmacy() {
        // When
        final KakaoApiResponse kakaoApiResponse = service.requestAddressSearch(주소);
        // Then
        assertThat(kakaoApiResponse).isNotNull();
    }
}
