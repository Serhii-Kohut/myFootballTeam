package com.serhii.myproject.controller;

import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.Player;
import com.serhii.myproject.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
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
    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model){
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));
        return "player-info";
    }
}
