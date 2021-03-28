package com.example.demo.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filtering internal " + request + " " + request.getHeader(SecurityConstants.HEADER));

        String username = getUsernameFromJwt(request);
        if (!username.isEmpty() && !authenticated())
            SecurityContextHolder.getContext().setAuthentication(
                    getAuthenticationToken(username));
        chain.doFilter(request, response);
    }

    private boolean authenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private Authentication getAuthenticationToken(String username) {
        System.out.println("Generating auth token for " + username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, null, Collections.emptyList());
        System.out.println(token);
        return token;
    }

    private String getUsernameFromJwt(HttpServletRequest request) {
        String jwt = request.getHeader(SecurityConstants.HEADER);
        if (jwt != null && jwt.startsWith(SecurityConstants.PREFIX)) {
            System.out.println("Jwt in header: " + jwt);
            return new JwtUtils().getSubject(jwt);
        }
        return "";
    }
}
