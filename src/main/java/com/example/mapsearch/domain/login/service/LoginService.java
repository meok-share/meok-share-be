package com.example.mapsearch.domain.login.service;

import com.example.mapsearch.config.provider.JwtTokenProvider;
import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.Tokens;
import com.example.mapsearch.domain.login.entity.User;
import com.example.mapsearch.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public Tokens login(LoginReq req) {
        User member = findUser(req);

        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createTokens(member.getUsername(), member.getRoles());
    }

    private User findUser(final LoginReq req) {
        return userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
    }

    @Transactional
    public void join(final LoginReq user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 E-MAIL 입니다.");
        }

        userRepository.save(User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .nickName(user.getNickName())
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build());
    }

}
