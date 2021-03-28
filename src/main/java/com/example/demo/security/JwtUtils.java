package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtils {
    public String create(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET));
    }

    public String getSubject(String jwt) {
        jwt = jwt.replace(SecurityConstants.PREFIX, "");
        return JWT
                .require(Algorithm.HMAC512(SecurityConstants.SECRET))
                .build()
                .verify(jwt)
                .getSubject();
    }
}
