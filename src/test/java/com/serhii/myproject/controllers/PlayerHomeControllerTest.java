package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.TestSecurityConfig;
import com.serhii.myproject.controller.PlayerHomeController;
import com.serhii.myproject.model.PlayerPosition;
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
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PlayerHomeController.class)
@ContextConfiguration(classes = {PlayerHomeController.class, TestSecurityConfig.class})
public class PlayerHomeControllerTest {
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
    public void testPlayerHomePageWhenAuthenticated() throws Exception {
        // Макети для сервісу гравця та компонента заголовка

        when(playerService.getByPosition(PlayerPosition.GOALKEEPER)).thenReturn(Collections.emptyList());
        when(playerService.getByPosition(PlayerPosition.DEFENDER)).thenReturn(Collections.emptyList());
        when(playerService.getByPosition(PlayerPosition.MIDFIELDER)).thenReturn(Collections.emptyList());
        when(playerService.getByPosition(PlayerPosition.FORWARD)).thenReturn(Collections.emptyList());

        // GET-запит до /player-home

        mockMvc.perform(get("/player-home"))
                .andExpect(status().isOk())
                .andExpect(view().name("player-home"))
                .andExpect(model().attributeExists("goalkeepers"))
                .andExpect(model().attributeExists("defenders"))
                .andExpect(model().attributeExists("midfielders"))
                .andExpect(model().attributeExists("forwards"));

        verify(playerService).getByPosition(PlayerPosition.GOALKEEPER);
        verify(playerService).getByPosition(PlayerPosition.DEFENDER);
        verify(playerService).getByPosition(PlayerPosition.MIDFIELDER);
        verify(playerService).getByPosition(PlayerPosition.FORWARD);

        verify(headerComponent).addUserToModel(any(Model.class), any(Principal.class));

    }

}
