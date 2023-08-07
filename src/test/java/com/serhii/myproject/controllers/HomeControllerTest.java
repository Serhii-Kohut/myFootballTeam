package com.serhii.myproject.controllers;

import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.security.Principal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testHomePage() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        mockMvc.perform(get("/home").principal(new Principal() {
                    @Override
                    public String getName() {
                        return "test@test.com";
                    }
                }))
                .andExpect(status().isOk())
                .andExpect(view().name("home-page"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

    }

}
