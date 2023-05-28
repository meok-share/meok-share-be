package com.example.mapsearch.config.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mapsearch.config.VerifyResult;
import com.example.mapsearch.domain.login.entity.MUsers;

import java.time.Instant;

public class JWTUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC512("meokShare");

    private static final long AUTH_TIME = 20 * 60;

    private static final long REFRESH_TIME = 60 * 60 * 24 * 7;

    public static String makeAuthToken(MUsers user) {
        return JWT.create().withSubject(user.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(Algorithm.HMAC512(String.valueOf(ALGORITHM)));

    }

    public static String makeRefreshToken(MUsers user) {
        return JWT.create().withSubject(user.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .sign(Algorithm.HMAC512(String.valueOf(ALGORITHM)));
    }

    public static VerifyResult verify(String token) {
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);

            return VerifyResult.builder()
                    .success(true)
                    .userEmail(verify.getSubject())
                    .build();
        } catch (Exception ex) {
            final DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .userEmail(decode.getSubject())
                    .build();
        }
    }

}
