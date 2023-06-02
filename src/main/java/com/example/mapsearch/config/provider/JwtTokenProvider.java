package com.example.mapsearch.config.provider;

import com.example.mapsearch.domain.login.dto.Tokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "meockshare";

    private static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";

    private long tokenValidTime = 60 * 60 * 1000L * 24; // 1 days

    private long refreshTokenValidTime = 60 * 60 * 1000L * 24 * 7; // 7 days

    private final UserDetailsService userDetailsService;

    // 객체 초기화, secretKey 를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public Tokens createTokens(String userPk, List<String> roles) {
        Date now = new Date();
        Date accessTokenExpiryDate = new Date(now.getTime() + tokenValidTime);
        Date refreshTokenExpiryDate = new Date(now.getTime() + refreshTokenValidTime);

        String accessToken = generateToken(userPk, roles, accessTokenExpiryDate);
        String refreshToken = generateToken(userPk, roles, refreshTokenExpiryDate);

        return new Tokens(accessToken, refreshToken);
    }

    private String generateToken(String userPk, List<String> roles, Date expiryDate) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Request 의 Header 에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN 값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(X_AUTH_TOKEN);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}