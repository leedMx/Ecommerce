package com.example.demo.model.requests;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String username;
    private String password;
}
