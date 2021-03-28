package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username) {
        log.info("Submitting order");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("user not found");
            return ResponseEntity.notFound().build();
        }
        log.debug(user.toString());
        UserOrder order = UserOrder.createFromCart(user.getCart());
        log.debug(order.toString());
        orderRepository.save(order);
        log.info("Order successfully sent");
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        log.info("Querying orders");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("User not found");
            return ResponseEntity.notFound().build();
        }
        log.debug(user.toString());
        return ResponseEntity.ok(orderRepository.findByUser(user));
    }
}
