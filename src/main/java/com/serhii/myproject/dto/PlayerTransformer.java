package com.serhii.myproject.dto;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;

public class PlayerTransformer {
    public static PlayerDto convertToDto(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getUserId(),
                player.getPlayerPosition().toString(),
                player.getDateOfBirth(),
                player.getCountry(),
                player.getImportantNotes()
        );
    }

    public static Player convertToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setUserId(playerDto.getUserId());
        player.setPlayerPosition(PlayerPosition.valueOf(playerDto.getPosition()));
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setCountry(playerDto.getCountry());
        player.setImportantNotes(playerDto.getImportantNotes());
        return player;
    }
}

