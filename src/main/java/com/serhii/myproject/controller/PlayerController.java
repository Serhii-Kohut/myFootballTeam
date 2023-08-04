package com.serhii.myproject.controller;

import com.serhii.myproject.component.HeaderComponent;
import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.Player;
import com.serhii.myproject.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/players")
public class PlayerController {
    Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerService playerService;
    private final HeaderComponent headerComponent;

    public PlayerController(PlayerService playerService, HeaderComponent headerComponent) {
        this.playerService = playerService;
        this.headerComponent = headerComponent;
    }

    @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @GetMapping("/create")
    public String showCreateFormPlayer(Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("player", new PlayerDto());

        logger.info("Create player page was showed");

        return "create-player";
    }

    @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @PostMapping("/create")
    public String createPlayer(@ModelAttribute("playerDto") PlayerDto playerDto) {
        Player player = PlayerTransformer.convertToEntity(playerDto);
        playerService.create(player);

        logger.info("New player was added");

        return "redirect:/player-home";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));

        logger.info("Player info page was showed");

        return "player-info";
    }

    @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @GetMapping("/{id}/update")
    public String showUpdateFormPlayer(@PathVariable("id") long id, Model model, Principal principal) {
        headerComponent.addUserToModel(model, principal);
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));

        logger.info("Update player page was showed");

        return "update-player";
    }

    @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @PostMapping("/update")
    public String updatePlayer(@ModelAttribute PlayerDto playerDto) {
        // Отримати оригінальний об'єкт Player з бази даних за допомогою id
        Player originalPlayer = playerService.readById(playerDto.getId());

        if (originalPlayer != null) {
            // Оновити поля оригінального об'єкта Player за допомогою даних з форми
            Player updatedPlayer = PlayerTransformer.convertToEntity(playerDto);
            BeanUtils.copyProperties(updatedPlayer, originalPlayer);

            // Зберегти оновлені дані в базі даних
            playerService.update(originalPlayer);
        }

        logger.info("Player data was updated");

        return "redirect:/player-home";
    }

    @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @GetMapping("/{id}/delete")
    private String delete(@PathVariable(    "id") long id) {
        playerService.delete(id);

        logger.info("Player was deleted");

        return "redirect:/player-home";
    }
}
