package com.example.mapsearch.domain.login.controller;

import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.LoginRes;
import com.example.mapsearch.domain.login.entity.User;
import com.example.mapsearch.domain.login.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generate and return a token
        String token = generateToken(user);

        LoginRes loginResponse = new LoginRes(token);
        return ResponseEntity.ok(loginResponse);
    }

    private String generateToken(User user) {
        // TODO: refactor
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(4);
        Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

        String secretKey = "your_secret_key";
        String token = Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return token;
    }

}
