package com.serhii.myproject.dto;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;
import com.serhii.myproject.model.User;
import com.serhii.myproject.service.UserService;

public class PlayerTransformer {
    public static PlayerDto convertToDto(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getPosition().toString(),
                player.getDateOfBirth(),
                player.getCountry(),
                player.getImportantNotes(),
                player.getMarketValue(),
                player.getPlayerFirstName(),
                player.getPlayerLastName()
        );
    }

    public static Player convertToEntity(PlayerDto playerDto) {
        Player player = new Player();

        player.setId(playerDto.getId());
        player.setPosition(PlayerPosition.valueOf(playerDto.getPosition()));
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setCountry(playerDto.getCountry());
        player.setImportantNotes(playerDto.getImportantNotes());
        player.setMarketValue(playerDto.getMarketValue());
        player.setPlayerFirstName(playerDto.getPlayerFirstName());
        player.setPlayerLastName(playerDto.getPlayerLastName());
        return player;
    }
}

