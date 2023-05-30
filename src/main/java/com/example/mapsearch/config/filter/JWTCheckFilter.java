package com.example.mapsearch.config.filter;

import com.example.mapsearch.config.VerifyResult;
import com.example.mapsearch.config.util.JWTUtil;
import com.example.mapsearch.domain.login.LoginService;
import com.example.mapsearch.domain.login.entity.MUsers;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 매번 request 가 올때마다 token 을 검사후 security ContextHolder 에 user 정보를 채워주는 역할
 */
public class JWTCheckFilter extends BasicAuthenticationFilter {

    private LoginService loginService;

    public JWTCheckFilter(final AuthenticationManager authenticationManager, LoginService loginService) {
        super(authenticationManager);
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null && !bearer.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = bearer.substring("Bearer ".length());
        VerifyResult result = JWTUtil.verify(token);

        if (result.isSuccess()) {
            MUsers user = loginService.loadUserByUsername(result.getUserEmail());

            final UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(), null, user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request, response);
        } else {
            throw new AuthenticationException("Token is not valid");
        }
    }

}
