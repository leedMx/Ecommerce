package com.example.demo.controllers;

import com.example.demo.model.requests.AuthenticateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtEndpoint {
    private final AuthenticationManager manager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest r) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            r.getUsername(), r.getPassword());
            manager.authenticate(token);
            return ResponseEntity.ok("jwt");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(
                    e.getClass()  + " " +e.getCause() + " " + e.getMessage());
        }
    }
}
