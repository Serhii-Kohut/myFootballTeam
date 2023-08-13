package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.TestSecurityConfig;
import com.serhii.myproject.controller.ManagersHomeController;
import com.serhii.myproject.dto.UserDto;
import com.serhii.myproject.dto.UserTransformer;
import com.serhii.myproject.model.Role;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ManagersHomeController.class)
@ContextConfiguration(classes = {ManagersHomeController.class, TestSecurityConfig.class})
public class ManagersHomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private HeaderComponent headerComponent;
    @MockBean
    private CustomUserDetails customUserDetails;

    @Test
    @WithMockUser(username = "scaroni@gmail.com", authorities = {"PRESIDENT"})
    public void testHomePageManagersWhenAuthenticated() throws Exception {
        User user = new User();
        user.setFirstName("Valerio");
        user.setLastName("Fiori");
        user.setEmail("scaroni@gmail.com");
        user.setRole(Role.valueOf("PRESIDENT"));
        List<User> users = Collections.singletonList(user);
        when(userService.getAllUsers()).thenReturn(users);

        List<UserDto> userDtos = users.stream()
                .map(UserTransformer::convertToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/managers-home"))
                .andExpect(status().isOk())
                .andExpect(view().name("managers-home"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAllUsers();
    }


    @Test
    public void testHomePageManagersWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/managers-home"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost/custom-login"));
    }
}



