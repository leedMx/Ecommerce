package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

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
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User u = findByUsername(username);
            if (u == null)
                throw new UsernameNotFoundException(username);
            return new org.springframework.security.core.userdetails.User
                    (u.getUsername(), u.getPassword(), Collections.emptyList());
    }
}
