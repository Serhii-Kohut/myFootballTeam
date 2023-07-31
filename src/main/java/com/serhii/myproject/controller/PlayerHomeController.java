package com.serhii.myproject.controller;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.PlayerPosition;
import com.serhii.myproject.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlayerHomeController {
    private final PlayerService playerService;
    private final HeaderComponent headerComponent;

    public PlayerHomeController(PlayerService playerService, HeaderComponent headerComponent) {
        this.playerService = playerService;
        this.headerComponent = headerComponent;
    }

    @GetMapping("/player-home")
    public String showPlayerHome(Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);

        List<PlayerDto> goalkeepersPlayerDtos = playerService.getByPosition(PlayerPosition.GOALKEEPER)
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparingLong(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("goalkeepers", goalkeepersPlayerDtos);

        List<PlayerDto> defendersPlayerDtos = playerService.getByPosition(PlayerPosition.DEFENDER)
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparingLong(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("defenders", defendersPlayerDtos);

        List<PlayerDto> midfieldersPlayerDtos = playerService.getByPosition(PlayerPosition.MIDFIELDER)
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparingLong(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("midfielders", midfieldersPlayerDtos);

        List<PlayerDto> forwardsPlayerDtos = playerService.getByPosition(PlayerPosition.FORWARD)
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparingLong(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("forwards", forwardsPlayerDtos);

        return "player-home";
    }


}
