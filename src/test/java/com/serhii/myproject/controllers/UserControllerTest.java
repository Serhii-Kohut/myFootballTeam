package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.SecurityConfig;
import com.serhii.myproject.controller.UserController;
import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, SecurityConfig.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private HeaderComponent headerComponent;

    @MockBean
    private CustomUserDetails customUserDetails;

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testShowCreateFormWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/users/create").principal(() -> "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-user"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    public void testShowCreateFormWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Test");
        userDto.setLastName("User");


        mockMvc.perform(post("/users/create")
                        .with(csrf())
                        .param("email", "test@example.com")
                        .param("firstName", "Test")
                        .param("lastName", "User"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/managers-home")
                );

        User expectedUser = UserTransformer.convertToEntity(userDto);
        verify(userService).create(expectedUser);
    }
}
