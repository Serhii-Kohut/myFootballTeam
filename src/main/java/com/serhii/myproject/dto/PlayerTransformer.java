package com.serhii.myproject.dto;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;

public class PlayerTransformer {
    public static PlayerDto convertToDto(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getUserId(),
                player.getPosition().toString(),
                player.getDateOfBirth(),
                player.getCountry(),
                player.getImportantNotes(),
                player.getMarketValue()
        );
    }

    public static Player convertToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setUserId(playerDto.getUserId());
        player.setPosition(PlayerPosition.valueOf(playerDto.getPosition()));
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setCountry(playerDto.getCountry());
        player.setImportantNotes(playerDto.getImportantNotes());
        player.setMarketValue(playerDto.getMarketValue());
        return player;
    }
}

