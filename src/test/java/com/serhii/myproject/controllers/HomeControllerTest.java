package com.serhii.myproject.controllers;

import com.serhii.myproject.config.SecurityConfig;
import com.serhii.myproject.controller.HomeController;
import com.serhii.myproject.model.User;
import com.serhii.myproject.repository.UserRepository;
import com.serhii.myproject.service.impl.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
@ContextConfiguration(classes = {HomeController.class, SecurityConfig.class})
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CustomUserDetails customUserDetails;

    @Test
    @WithMockUser(username = "test@example.com")
    public void testHomePageWhenAuthenticated() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        mockMvc.perform(get("/home").principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("home-page"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void testHomePageWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));
    }
}

