package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final PasswordEncoder encoder;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User save(String username, String password) {
        if (userRepository.existsByUsername(username))
            throw new IllegalArgumentException("Username unavailable");
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return user;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.valueOf(id)));
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new NoSuchElementException(username);
        return user;
    }

    public String authenticate(String username, String password) throws AuthenticationException {
        Authentication auth = new UsernamePasswordAuthenticationToken(username,password, Collections.emptyList());
        authenticationManager.authenticate(auth);
        return null;
    }
}
