package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private static UserController controller;
    private static final UserService service = mock(UserService.class);

    @BeforeClass
    public static void setUp(){
        controller = new UserController(service);
    }

    @Before
    public void mocks(){
        User user = new User();
        when(service.findById(1L)).thenReturn(user);
        when(service.findByUsername("test")).thenReturn(user);
    }

    @Test
    public void findById() {
        ResponseEntity<?> response = controller.findById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void findByBadId() {
        ResponseEntity<?> response = controller.findById(999L);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void findByUserName() {
        ResponseEntity<?> response = controller.findByUserName("test");
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void findByBadUserName() {
        ResponseEntity<?> response = controller.findByUserName("bad");
        assertEquals(400,response.getStatusCodeValue());
    }
}