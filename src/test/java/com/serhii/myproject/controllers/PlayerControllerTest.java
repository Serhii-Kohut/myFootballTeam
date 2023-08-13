package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.TestSecurityConfig;
import com.serhii.myproject.controller.PlayerController;
import com.serhii.myproject.service.PlayerService;
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

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
@ContextConfiguration(classes = {PlayerController.class, TestSecurityConfig.class})
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private HeaderComponent headerComponent;

    @MockBean
    private CustomUserDetails customUserDetails;

    @Test
    @WithMockUser(username = "test@example.com", authorities = "SPORT_DIRECTOR")
    public void testShowCreatePlayerFormWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/players/create").principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-player"))
                .andExpect(model().attributeExists("player"));
    }

    @Test
    public void testShowCreatePlayerFormWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/players/create"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));
    }

}
