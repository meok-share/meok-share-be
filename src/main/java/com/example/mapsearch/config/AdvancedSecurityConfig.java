package com.example.mapsearch.config;

import com.amazonaws.event.ProgressListener;
import com.example.mapsearch.config.filter.JWTCheckFilter;
import com.example.mapsearch.config.filter.JWTLoginFilter;
import com.example.mapsearch.domain.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdvancedSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final LoginService loginService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new PasswordEncoder() {
//            @Override
//            public String encode(final CharSequence rawPassword) {
//                return rawPassword.toString();
//            }
//
//            @Override
//            public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
//                return rawPassword.toString().equals(encodedPassword);
//            }
//        };
    }

    public AdvancedSecurityConfig(final LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), loginService);

        http.csrf().disable()
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
        ;
    }

}
