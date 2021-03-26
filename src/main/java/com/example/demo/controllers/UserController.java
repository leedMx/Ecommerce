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

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder encoder;

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
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            return ResponseEntity.badRequest()
                    .body("Passwords do not match");
        if (request.getPassword().length() < 7)
            return ResponseEntity.badRequest()
                    .body("Password is too short");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        Cart car = new Cart();

        cartRepository.save(car);
        user.setCart(car);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
