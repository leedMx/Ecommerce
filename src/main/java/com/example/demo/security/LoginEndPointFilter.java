package com.example.demo.security;

import com.example.demo.model.requests.AuthenticateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginEndPointFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager manager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticateRequest user = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticateRequest.class);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    );
            return manager.authenticate(token);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = getUser(authResult).getUsername();
        String jwt = new JwtUtils().create(username);
        response.addHeader(SecurityConstants.HEADER, SecurityConstants.PREFIX + jwt);
    }

    private org.springframework.security.core.userdetails.User getUser(Authentication authResult) {
        return (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
    }
}
