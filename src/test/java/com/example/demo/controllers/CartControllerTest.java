package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class CartControllerTest {
    private static CartController controller;
    private static final UserRepository userRepo = mock(UserRepository.class);
    private static final CartRepository cartRepo = mock(CartRepository.class);
    private static final ItemRepository itemRepo = mock(ItemRepository.class);

    private ObjectMapper mapper = new ObjectMapper();


    @BeforeClass
    public static void setUp() throws Exception {
        controller = new CartController(userRepo, cartRepo, itemRepo);
    }

    @Before
    public void setMocks() {
        User user = new User();
        user.setUsername("test");
        user.setCart(new Cart());
        Mockito.when(userRepo.findByUsername("test")).thenReturn(user);

        Item item = new Item();
        item.setDescription("Test Item");
        item.setName("testItem");
        item.setPrice(BigDecimal.valueOf(1.11));
        item.setId(1L);
        Mockito.when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
    }

    @Test
    public void addTocart() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setQuantity(2);
        request.setItemId(1);

        ResponseEntity<Cart> response = controller.addTocart(request);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        double actual = cart.getTotal().doubleValue();
        assertEquals(2.22, actual, 0.01);
    }

    @Test
    public void removeFromcart() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setQuantity(1);
        request.setItemId(1);

        ResponseEntity<Cart> response = controller.removeFromcart(request);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        double actual = cart.getTotal().doubleValue();
        assertEquals(0, actual, 0.01);
    }

    @Test
    public void addToCartBadUsername() throws Exception {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("bad");
        request.setQuantity(1);
        request.setItemId(1);

        ResponseEntity<Cart> response = controller.addTocart(request);
        assertEquals(404, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNull(cart);
    }

    @Test
    public void addToCartBadItem() throws Exception {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setQuantity(1);
        request.setItemId(999);

        ResponseEntity<Cart> response = controller.addTocart(request);
        assertEquals(404, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNull(cart);
    }

    @Test
    public void removeFromCartBadUsername() throws Exception {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("bad");
        request.setQuantity(1);
        request.setItemId(1);

        ResponseEntity<Cart> response = controller.removeFromcart(request);
        assertEquals(404, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNull(cart);
    }

    @Test
    public void removeFromCartBadItem() throws Exception {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setQuantity(1);
        request.setItemId(999);

        ResponseEntity<Cart> response = controller.removeFromcart(request);
        assertEquals(404, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNull(cart);
    }
}