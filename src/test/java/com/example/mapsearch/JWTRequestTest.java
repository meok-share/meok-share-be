package com.example.mapsearch;

import com.example.mapsearch.domain.login.LoginService;
import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.LoginRes;
import com.example.mapsearch.domain.login.entity.MUsers;
import com.example.mapsearch.domain.login.repository.UserRepository;
import com.example.mapsearch.integration.WebIntefrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JWTRequestTest extends WebIntefrationTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    private static final String BEARER = "Bearer ";

    private static final String AUTH_TOKEN = "auth_token";

    private static final String REFRESH_TOKEN = "refresh_token";


    private final MUsers USER = MUsers.builder()
            .userId(0L)
            .email("tkaqkeldk@naver.com")
            .password("1234")
            .userName("신형기")
            .enabled(true)
            .build();

    @BeforeEach
    void before() {
        userRepository.deleteAll();

        loginService.join(USER);
    }

    private TokenBox getToken() {
        // given
        RestTemplate client = new RestTemplate();
        // when
        HttpEntity<LoginReq> body = new HttpEntity<>(new LoginReq("tkaqkeldk@naver.com", "1234", null));
        final ResponseEntity<LoginRes> response = client.exchange(uri("/login"), HttpMethod.POST, body, LoginRes.class);
        final String authToken = response.getHeaders().get(AUTH_TOKEN).get(0);
        final String refreshToken = response.getHeaders().get(REFRESH_TOKEN).get(0);

        return TokenBox.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private TokenBox getRefreshToken(String token) {
        // given
        RestTemplate client = new RestTemplate();
        // when
        HttpEntity<LoginReq> body = new HttpEntity<>(new LoginReq(null, null, token));
        final ResponseEntity<LoginRes> response = client.exchange(uri("/login"), HttpMethod.POST, body, LoginRes.class);
        final String authToken = response.getHeaders().get(AUTH_TOKEN).get(0);
        final String refreshToken = response.getHeaders().get(REFRESH_TOKEN).get(0);

        return TokenBox.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Test
    @DisplayName("로그인_후_토큰을_받는다")
    void loginAfterGetToken() {

        // Given
        final TokenBox token = getToken();

        RestTemplate client = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, BEARER + token.getAuthToken());

        // When
        HttpEntity body = new HttpEntity<>(null, header);
        final ResponseEntity<String> exchange = client.exchange(uri("/login/hello"), HttpMethod.GET, body, String.class);

        // Then
        assertEquals("hello", exchange.getBody());

    }

    @Test
    @DisplayName("토큰 만료 테스트")
    void tokenExpireTest() throws InterruptedException {
        // given
        TokenBox token = getToken();
        Thread.sleep(3000);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, BEARER + token.getAuthToken());

        RestTemplate client = new RestTemplate();
        assertThrows(Exception.class, () -> {
            HttpEntity body = new HttpEntity<>(null, header);
            ResponseEntity<String> res1 = client.exchange(uri("/login/hello"), HttpMethod.GET, body, String.class);
        });

        token = getRefreshToken(token.getRefreshToken());

        HttpHeaders header2 = new HttpHeaders();
        header2.add(HttpHeaders.AUTHORIZATION, BEARER + token.getAuthToken());
        HttpEntity body = new HttpEntity<>(null, header2);
        ResponseEntity<String> res2 = client.exchange(uri("/login/hello"), HttpMethod.GET, body, String.class);


        assertEquals("hello", res2.getBody());
    }

}
