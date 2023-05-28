package com.example.mapsearch.domain.login;

import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.LoginRes;
import com.example.mapsearch.domain.login.entity.MUsers;
import com.example.mapsearch.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginRes login(LoginReq loginRequest) {
        Optional<MUsers> optionalUser = userRepository.findUserByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 사용자 입니다.");
        }

        MUsers user = optionalUser.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치 하지 않습니다.");
        }

        // Generate and return a token
//        String token = generateToken(user);

//        return new LoginRes(token);
        return null;
    }

    @Transactional
    public void join(final MUsers user) {
        userRepository.save(user);
    }

    @Override
    public MUsers loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException(username));
    }

//    private String generateToken(Users user) {
//        // TODO: refactor
//        LocalDateTime expirationTime = LocalDateTime.now().plusHours(4);
//        Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
//
//        // TODO: deployScript 에서 secretKey 를 환경변수로 설정하도록 변경
//        String secretKey = "test";
//
//        String token = JWT.builder()
//                .setSubject(user.getUserName())
//                .setIssuedAt(new Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//
//        return token;
//    }

}
