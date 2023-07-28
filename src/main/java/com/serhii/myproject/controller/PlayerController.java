package com.serhii.myproject.controller;

import com.serhii.myproject.dto.PlayerDto;
import com.serhii.myproject.dto.PlayerTransformer;
import com.serhii.myproject.model.Player;
import com.serhii.myproject.service.PlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

 //   @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
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
  //  @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @GetMapping("/{id}/update")
    public String showUpdateFormPlayer(@PathVariable("id") long id, Model model) {
        model.addAttribute("player", PlayerTransformer.convertToDto(playerService.readById(id)));
        return "update-player";
    }
  //  @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
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


        return "redirect:/player-home";
    }

 //   @PreAuthorize("hasRole('PRESIDENT') or hasRole('SPORT_DIRECTOR')")
    @GetMapping("/{id}/delete")
    private String delete(@PathVariable("id") long id) {
        playerService.delete(id);
        return "redirect:/player-home";
    }
}
