package com.example.mapsearch;

import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.entity.User;
import com.example.mapsearch.domain.login.repository.UserRepository;
import com.example.mapsearch.domain.login.service.LoginService;
import com.example.mapsearch.integration.WebIntefrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JWTRequestTest extends WebIntefrationTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    private final User USER = User.builder()
            .id(0L)
            .email("tkaqkeldk@naver.com")
            .password("1234")
            .build();

    @BeforeEach
    void before() {
        userRepository.deleteAll();

        loginService.join(new LoginReq(USER.getEmail(), USER.getPassword()));
    }


    @Test
    @DisplayName("로그인_후_토큰을_받는다")
    void loginAfterGetToken() {
        // When & Then
        final String login = loginService.login(new LoginReq(USER.getEmail(), USER.getPassword()));
        assertThat(login).isNotNull();
    }

    @Test
    @DisplayName("토큰 만료 테스트")
    void tokenExpireTest() throws InterruptedException {

        // TODO : 토큰 만료 테스트
    }

}
