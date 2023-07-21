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
    public String read(@PathVariable("id") long id, Model model) {
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));
        return "player-info";
    }

    @GetMapping("/{id}/update")
    public String showUpdateFormPlayer(@PathVariable("id") long id, Model model) {
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));
        return "update-player";
    }

    @PostMapping("/update")
    public String updatePlayer(@ModelAttribute PlayerDto playerDto) {
        playerService.update(PlayerTransformer.convertToEntity(playerDto));
        return "redirect:/player-home";
    }

    @GetMapping("/{id}/delete")
    private String delete(@PathVariable("id") long id) {
        playerService.delete(id);
        return "redirect:/player-home";
    }
}
