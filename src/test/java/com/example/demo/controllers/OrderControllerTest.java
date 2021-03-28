package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private static OrderController controller;
    private static final UserRepository userRepo = mock(UserRepository.class);
    private static final OrderRepository orderRepo = mock(OrderRepository.class);

    @BeforeClass
    public static void setUp() throws Exception {
        controller = new OrderController(userRepo,orderRepo);
    }

    @Before
    public void mocks(){
        Item item = new Item();
        item.setPrice(new BigDecimal(1.11));
        item.setName("testItem");
        item.setId(1L);
        Cart cart = new Cart();
        cart.addItem(item);
        User user = new User();
        user.setUsername("test");
        user.setCart(cart);
        when(userRepo.findByUsername("test")).thenReturn(user);
    }

    @Test
    public void submit() {
        ResponseEntity<UserOrder> response =
                controller.submit("test");

        assertEquals(200, response.getStatusCodeValue());
        UserOrder actual = response.getBody();
        assertEquals(1,actual.getItems().size());
        Double total = actual.getTotal().doubleValue();
        assertEquals(1.11, total , 0.01);
    }

    @Test
    public void submitBadUser() {
        ResponseEntity<UserOrder> response =
                controller.submit("");

        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUser() {
        ResponseEntity<List<UserOrder>> response =
                controller.getOrdersForUser("test");
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void getOrdersForBadUser() {
        ResponseEntity<List<UserOrder>> response =
                controller.getOrdersForUser("bad");
        assertEquals(404,response.getStatusCodeValue());
    }
}