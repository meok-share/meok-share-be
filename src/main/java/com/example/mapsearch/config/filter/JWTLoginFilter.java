package com.example.mapsearch.config.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.mapsearch.config.VerifyResult;
import com.example.mapsearch.config.util.JWTUtil;
import com.example.mapsearch.domain.login.LoginService;
import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.entity.MUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 처리를 해주는 login Filter
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String BEARER = "Bearer ";

    private static final String AUTH_TOKEN = "auth_token";

    private static final String REFRESH_TOKEN = "refresh_token";

    private final LoginService loginService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, LoginService loginService) {
        super(authenticationManager);
        this.loginService = loginService;
        setFilterProcessesUrl("/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginReq loginReq = parseLoginRequest(request);

        // 리프레시 토큰이 있는지 확인.
        if (loginReq.getRefreshToken() == null) {
            // 사용자 이름과 비밀번호로 인증
            return authenticateWithUsernamePassword(loginReq.getEmail(), loginReq.getPassword());
        } else {
            // Authenticate with the refresh token
            return authenticateWithRefreshToken(loginReq.getRefreshToken());
        }
    }

    /**
     * 요청 본문에서 로그인 요청을 파싱
     *
     * @param request
     * @return
     */
    private LoginReq parseLoginRequest(HttpServletRequest request) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return objectMapper.readValue(request.getInputStream(), LoginReq.class);
        } catch (IOException ex) {
            throw new AuthenticationServiceException("Error occurred during authentication", ex);
        }
    }

    /**
     * 주어진 이메일과 비밀번호로 UsernamePasswordAuthenticationToken 을 생성 후
     * 인증 매니저를 사용하여 인증.
     *
     * @param email
     * @param password
     * @return
     */
    private Authentication authenticateWithUsernamePassword(String email, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, null);

        return getAuthenticationManager().authenticate(token);
    }

    /**
     * 리프레시 토큰으로 인증
     * 검증된 토큰에서 사용자 상세 정보를 조회.
     * 만료되었을시 리프레시 토큰이 만료된 경우 예외 처리.
     *
     * @param refreshToken
     * @return
     */
    private Authentication authenticateWithRefreshToken(String refreshToken) {
        VerifyResult verify = JWTUtil.verify(refreshToken);

        if (verify.isSuccess()) {
            MUsers user = loginService.loadUserByUsername(verify.getUserEmail());
            return new UsernamePasswordAuthenticationToken(user, user.getAuthorities());
        } else {
            throw new TokenExpiredException("Refresh Token is expired");
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult) throws IOException, ServletException {
        final MUsers user = (MUsers) authResult.getPrincipal();

        response.setHeader(AUTH_TOKEN, JWTUtil.makeAuthToken(user));
        response.setHeader(REFRESH_TOKEN, JWTUtil.makeRefreshToken(user));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
    }
}
