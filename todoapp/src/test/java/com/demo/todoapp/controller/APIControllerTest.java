package com.demo.todoapp.controller;

import com.demo.todoapp.service.TokenAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class APIControllerTest {
    @Autowired
    private MockMvc mvc;



    @Test
    void privateEndpoint() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/private").
                header("Authorization", "Bearer "+TokenAuthenticationService.createTokens()))
                .andExpect(status().isOk());
        //TokenAuthenticationService.createJWT("eehfqi","https://dev-1ffztey5.auth0.com/","zrfhr5tUR7iFSsJRTsmT4dG7Rs6zYOFu@clients",10*60*60)
    }

    @Test
    void shouldNotAllowAccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/private")).andExpect(status().isUnauthorized());
    }
    @Test
    void shouldAllowAccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/public")).andExpect(status().isOk());
    }
}