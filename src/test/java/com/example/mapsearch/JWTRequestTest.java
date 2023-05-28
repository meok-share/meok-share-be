package com.example.mapsearch;

import com.example.mapsearch.domain.login.LoginService;
import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.LoginRes;
import com.example.mapsearch.domain.login.entity.MUsers;
import com.example.mapsearch.domain.login.repository.UserRepository;
import com.example.mapsearch.integration.WebIntefrationTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class JWTRequestTest extends WebIntefrationTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

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

    @Test
    @DisplayName("hello 메세지를 가져온다")
    void hello_메세지를_가져온다() {
        // given
        RestTemplate client = new RestTemplate();
        // when
        HttpEntity<LoginReq> body = new HttpEntity<>(new LoginReq("tkaqkeldk@naver.com", "1234"));
        final ResponseEntity<LoginRes> response = client.exchange(uri("/login"), HttpMethod.POST, body, LoginRes.class);

        // then
        System.out.println("response.getHeaders() = " + response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        System.out.println("response = " + response.getBody());
    }

}
