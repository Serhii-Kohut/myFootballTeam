package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.TestSecurityConfig;
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

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class, TestSecurityConfig.class})
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

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testReadUser() throws Exception {
        long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail("test@example.com");
        when(userService.readById(id)).thenReturn(UserTransformer.convertToEntity(userDto));

        mockMvc.perform(get("/users/{id}/read", id))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info"))
                .andExpect(model().attributeExists("user"));

        verify(userService).readById(id);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testUpdateUserAuthorized() throws Exception {
        long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("test@example.com");
        when(userService.readById(userId)).thenReturn(UserTransformer.convertToEntity(userDto));

        mockMvc.perform(get("/users/{id}/update", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("update-user"))
                .andExpect(model().attributeExists("user"));

        verify(userService).readById(userId);
    }

    @Test
    public void testUpdateUserNotAuthorized() throws Exception {
        long userId = 1L;

        mockMvc.perform(get("/users/{id}/update", userId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Test");
        userDto.setLastName("User");


        mockMvc.perform(post("/users/update")
                        .with(csrf())
                        .param("email", "test@example.com")
                        .param("firstName", "Test")
                        .param("lastName", "User"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/managers-home")
                );

        verify(userService).update(UserTransformer.convertToEntity(userDto));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"PRESIDENT"})
    public void testDeleteUserAuthorized() throws Exception {
        long userId = 1L;

        mockMvc.perform(get("/users/{id}/delete", userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/managers-home"));

        verify(userService).delete(userId);
    }

    @Test
    public void testDeleteUserNotAuthorized() throws Exception {
        long userId = 1L;

        mockMvc.perform(get("/users/{id}/delete", userId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));

    }

}
