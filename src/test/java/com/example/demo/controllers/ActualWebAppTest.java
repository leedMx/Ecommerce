package com.example.demo.controllers;
//
//import com.example.demo.model.requests.CreateUserRequest;
//import com.example.demo.security.JwtUtils;
//import com.example.demo.security.SecurityConstants;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.Assert.assertEquals;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//public class ActualWebAppTest {
//    @Autowired
//    MockMvc mvc;
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    public void testCreateUser() throws Exception {
//        CreateUserRequest request = new CreateUserRequest();
//        request.setUsername("user");
//        request.setPassword("password");
//        request.setConfirmPassword("password");
//        String content = mapper.writeValueAsString(request);
//
//        mvc.perform(MockMvcRequestBuilders
//                .post("/api/user/create")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(content)
//        ).andExpect(status().isOk());
//    }
//
//    @Test
//    public void testUserBadPassword() throws Exception {
//        CreateUserRequest request = new CreateUserRequest();
//        request.setUsername("user");
//        request.setPassword("passwd");
//        request.setConfirmPassword("password");
//        String content = mapper.writeValueAsString(request);
//
//        String response = mvc.perform(MockMvcRequestBuilders
//                .post("/api/user/create")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(content))
//                .andExpect(status().isBadRequest())
//                .andReturn().getResponse().getContentAsString();
//        assertEquals("Passwords do not match", response);
//    }
//
//    @Test
//    public void testUserShortPassword() throws Exception {
//        CreateUserRequest request = new CreateUserRequest();
//        request.setUsername("user");
//        request.setPassword("pass");
//        request.setConfirmPassword("pass");
//        String content = mapper.writeValueAsString(request);
//
//        String response = mvc.perform(MockMvcRequestBuilders
//                .post("/api/user/create")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(content))
//                .andExpect(status().isBadRequest())
//                .andReturn().getResponse().getContentAsString();
//        assertEquals("Password is too short", response);
//    }
//
//    @Test
//    public void testEndpointIsForbidden() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                .get("/api/user/id/1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void testEndpointIsAccessibleWithJwt() throws Exception {
//        //create User to prevent flimsy test
//        CreateUserRequest request = new CreateUserRequest();
//        request.setUsername("test");
//        request.setPassword("password");
//        request.setConfirmPassword("password");
//        String content = mapper.writeValueAsString(request);
//        mvc.perform(MockMvcRequestBuilders
//                .post("/api/user/create")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(content)
//        ).andExpect(status().isOk());
//
//
//        String jwt = new JwtUtils().create("test");
//        System.out.println(jwt);
//        mvc.perform(MockMvcRequestBuilders
//                .get("/api/item")
//                .header(SecurityConstants.HEADER,
//                        SecurityConstants.PREFIX + jwt)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//    }
//
//}
