package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Arrays;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder encoder;
    private final int PASSWORD_MINIMUM_LENGTH = 7;

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user == null ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new IllegalArgumentException("Passwords do not match");
        if (request.getPassword().length() < PASSWORD_MINIMUM_LENGTH)
            throw new IllegalArgumentException("Password is too short");

        User user = newUser(request.getUsername(),request.getPassword());
        Cart car = new Cart();
        cartRepository.save(car);
        user.setCart(car);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    private User newUser(String username, String password) {
        String salt = createSalt();
        User user = new User();
        user.setUsername(username);
        user.setSalt(salt);
        user.setPassword(encoder.encode(password + salt));
        return user;
    }

    private String createSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Arrays.toString(salt);
    }
}
