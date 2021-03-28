package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private static ItemController controller;
    private static final ItemRepository repo = mock(ItemRepository.class);

    @BeforeClass
    public static void setUp() throws Exception {
        controller = new ItemController(repo);
    }

    @Before
    public void setMocks(){
        when(repo.findByName("name")).thenReturn(Arrays.asList(new Item()));
        when(repo.findById(1L)).thenReturn(Optional.of(new Item()));
    }

    @Test
    public void getItems() {
        assertEquals(200, controller.getItems().getStatusCodeValue());
    }

    @Test
    public void getItemById() {
        assertEquals(200,
                controller.getItemById(1L).getStatusCodeValue());
    }

    @Test
    public void getItemByBadId() {
        assertEquals(404,
                controller.getItemById(999L).getStatusCodeValue());
    }

    @Test
    public void getItemsByName() {
        assertEquals(200,
                controller.getItemsByName("name").getStatusCodeValue());
    }

    @Test
    public void getItemsByBadName() {
        assertEquals(404,
                controller.getItemsByName("none").getStatusCodeValue());
    }
}