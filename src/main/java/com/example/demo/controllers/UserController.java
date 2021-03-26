package com.example.demo.controllers;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity findByUserName(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findByUsername(username));
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        ResponseEntity.BodyBuilder bad = ResponseEntity.badRequest();
        try {
            if (!passwordMatch(request))
                return bad.body("Passwords do not match");
            if (!hasMinimumLength(request))
                return bad.body("Password is too short");
            return ResponseEntity.ok(userService.save(
                    request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            return bad.body(e.getMessage());
        }
    }

    private boolean passwordMatch(CreateUserRequest request) {
        return request.getPassword().equals(request.getConfirmPassword());
    }

    private boolean hasMinimumLength(CreateUserRequest request) {
        return request.getPassword().length() > 7;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(
            @RequestParam String username, @RequestParam String password) {

        return ResponseEntity.badRequest().body("Invalid Credentials");
    }
}
