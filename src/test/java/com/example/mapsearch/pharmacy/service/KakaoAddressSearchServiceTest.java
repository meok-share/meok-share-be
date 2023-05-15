package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.api.dto.Document;
import com.example.mapsearch.api.dto.KakaoApiResponse;
import com.example.mapsearch.api.service.KakaoAddressSearchService;
import com.example.mapsearch.api.service.KakaoUriBuilderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;

import java.net.URI;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class KakaoAddressSearchServiceTest {

    @Mock
    private TestRestTemplate restTemplate;

    @InjectMocks
    private KakaoUriBuilderService kakaoUriBuilderService;

    @Mock
    private RetryTemplate retryTemplate;

    @InjectMocks
    private KakaoAddressSearchService kakaoAddressSearchService;


    @Test
    @Deprecated
    @DisplayName("서버 에러 발생 시 Retry able 이 잘 작동하는지 테스트")
    void requestAddressSearch_retryOnServerError_shouldSucceedOnRetry() {
        // given
        String address = "서울시 강남구 역삼동";

        KakaoApiResponse kakaoApiResponse = new KakaoApiResponse();
        kakaoApiResponse.setDocumentList(Collections.singletonList(new Document()));
        ResponseEntity<KakaoApiResponse> responseEntity = new ResponseEntity<>(kakaoApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 88b2073ae586a8d628c80c6bc5d7f022");
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);

        // 첫번째 요청에서 서버 에러 발생
        Mockito.when(kakaoUriBuilderService.buildUriByAddressSearch(address))
                .thenReturn(URI.create("https://dapi.kakao.com/v2/local/search/address.json"));

        Mockito.when(restTemplate.exchange(
                        ArgumentMatchers.any(URI.class),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(HttpEntity.class),
                        ArgumentMatchers.<Class<KakaoApiResponse>>any())
                )
                .thenReturn(responseEntity)
                // 첫번째 요청에서 예외가 발생하도록 설정
                .thenThrow(new RuntimeException("Connection failed"))
                // 두번째 요청에서는 정상 응답이 돌아오도록 설정
                .thenReturn(new ResponseEntity<>(kakaoApiResponse, HttpStatus.OK));

        // when
        KakaoApiResponse actual = kakaoAddressSearchService.requestAddressSearch(address);

        // then
        assertThat(actual.getDocumentList().size()).isEqualTo(1);

        // 첫번째 요청은 실패했으므로 2번 호출되어야 함
        Mockito.verify(kakaoUriBuilderService, Mockito.times(2)).buildUriByAddressSearch(address);
    }


    @Test
    @DisplayName("정상적인 주소를 입력했을 경우, 위도, 경도로 변환된다.")
    void successSearch_x_y() {
        // TODO : 테스트 코드 작성
    }

}