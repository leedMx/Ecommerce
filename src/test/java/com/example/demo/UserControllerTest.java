package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mvc;
    ObjectMapper mapper = new ObjectMapper();
    @MockBean
    UserService userService;

    @Test
    public void testUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user");
        request.setPassword("password");
        request.setConfirmPassword("password");
        String content = mapper.writeValueAsString(request);
        System.out.println(content);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
        ).andExpect(status().isOk());
    }

    @Test
    public void testUserBadPassword() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user");
        request.setPassword("passwd");
        request.setConfirmPassword("password");
        String content = mapper.writeValueAsString(request);
        System.out.println(content);

        String response = mvc.perform(MockMvcRequestBuilders
                .post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Passwords do not match", response);
    }

    @Test
    public void testUserShortPassword() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("user");
        request.setPassword("pass");
        request.setConfirmPassword("pass");
        String content = mapper.writeValueAsString(request);
        System.out.println(content);

        String response = mvc.perform(MockMvcRequestBuilders
                .post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Password is too short", response);
    }
}
