package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureDataJpa
public class UserControllerTest {
    @Autowired
    MockMvc mvc;
    ObjectMapper mapper = new ObjectMapper();
    @MockBean
    UserRepository userRepository;
    @MockBean
    CartRepository cartRepository;

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
}
