package com.serhii.myproject.controllers;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.config.TestSecurityConfig;
import com.serhii.myproject.controller.PlayerController;
import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.Player;
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

    @Test
    @WithMockUser(username = "test@example.com", authorities = "SPORT_DIRECTOR")
    public void testCreatePlayer() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerFirstName("Noah");
        playerDto.setPlayerLastName("Okafor");
        playerDto.setJerseyNumber(17L);
        playerDto.setPosition("FORWARD");

        mockMvc.perform(post("/players/create").with(csrf())
                        .param("playerFirstName", "Noah")
                        .param("playerLastName", "Okafor")
                        .param("jerseyNumber", String.valueOf(17L))
                        .param("position", "FORWARD"))

                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/player-home"));
    }

    @Test
    @WithMockUser(username = "test@example.com", authorities = {"SPORT_DIRECTOR"})
    public void testReadPlayer() throws Exception {
        long id = 1L;
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerFirstName("Noah");
        playerDto.setPosition("FORWARD");

        when(playerService.readById(id)).thenReturn(PlayerTransformer.convertToEntity(playerDto));

        mockMvc.perform(get("/players/{id}/read", id))
                .andExpect(status().isOk())
                .andExpect(view().name("player-info"))
                .andExpect(model().attributeExists("player"));

        verify(playerService).readById(id);
    }

    @Test
    @WithMockUser(username = "test@example.com", authorities = {"SPORT_DIRECTOR"})
    public void testUpdatePlayer() throws Exception {
        long id = 1L;
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(id);
        playerDto.setPlayerFirstName("Noah");
        playerDto.setPlayerLastName("Okafor");
        playerDto.setPosition("FORWARD");

        when(playerService.readById(1L)).thenReturn(new Player());

        mockMvc.perform(post("/players/update").with(csrf())
                        .param("id", String.valueOf(1L))
                        .param("playerFirstName", "Noah")
                        .param("playerLastName", "Okafor")
                        .param("position", "FORWARD"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/player-home"));

        verify(playerService).update(PlayerTransformer.convertToEntity(playerDto));

    }

}
