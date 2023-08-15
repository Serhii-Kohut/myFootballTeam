package com.serhii.myproject.controllers;

import com.serhii.myproject.config.TestSecurityConfig;
import com.serhii.myproject.controller.LogInController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LogInControllerTest.class)
@ContextConfiguration(classes = {LogInController.class, TestSecurityConfig.class})
public class LogInControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/custom-login"))
                .andExpect(status().isOk())
                .andExpect(view().name("customLogin"));
    }
}
