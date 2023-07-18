package com.serhii.myproject.controller;

import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.Player;
import com.serhii.myproject.service.PlayerService;
import com.serhii.myproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final UserService userService;

    public PlayerController(PlayerService playerService, UserService userService) {
        this.playerService = playerService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateFormPlayer(Model model) {
        model.addAttribute("player", new PlayerDto());
        return "create-player";
    }

    @PostMapping("/create")
    public String createPlayer(@ModelAttribute("playerDto") PlayerDto playerDto) {
        Player player = PlayerTransformer.convertToEntity(playerDto);
        playerService.create(player);
        return "redirect:/player-home";
    }
}
