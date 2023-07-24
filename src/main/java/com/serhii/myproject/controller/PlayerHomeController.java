package com.serhii.myproject.controller;

import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.PlayerPosition;
import com.serhii.myproject.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlayerHomeController {
    private final PlayerService playerService;

    public PlayerHomeController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /*    @GetMapping("/player-home")
        public String showPlayerHome(Model model) {
            List<PlayerDto> playerDtos = playerService.getAllPlayers().stream()
                    .map(PlayerTransformer::convertToDto).sorted(Comparator.comparingLong(PlayerDto::getId))
                    .collect(Collectors.toList());

            model.addAttribute("players", playerDtos);
            return "player-home";
        }*/
    @GetMapping("/player-home/goalkeepers")
    public String showGoalkeepersPlayers(Model model) {
        List<PlayerDto> goalkeepersPlayerDtos = playerService.getByPosition(PlayerPosition.valueOf("Goalkeeper"))
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparing(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("players", goalkeepersPlayerDtos);
        return "player-list";
    }

    @GetMapping("/player-home/defenders")
    public String showDefendersPlayers(Model model) {
        List<PlayerDto> defendersPlayerDtos = playerService.getByPosition(PlayerPosition.valueOf("Defender"))
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparing(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("players", defendersPlayerDtos);
        return "player-list";
    }

    @GetMapping("/player-home/midfielders")
    public String showMidfieldersPlayers(Model model) {
        List<PlayerDto> midfieldersPlayerDtos = playerService.getByPosition(PlayerPosition.valueOf("Midfielder"))
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparing(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("players", midfieldersPlayerDtos);
        return "player-list";
    }

    @GetMapping("/player-home/forwards")
    public String showForwardsPlayers(Model model) {
        List<PlayerDto> forwardsPlayerDtos = playerService.getByPosition(PlayerPosition.valueOf("Forward"))
                .stream()
                .map(PlayerTransformer::convertToDto)
                .sorted(Comparator.comparing(PlayerDto::getId))
                .collect(Collectors.toList());

        model.addAttribute("players", forwardsPlayerDtos);
        return "player-list";
    }

}
