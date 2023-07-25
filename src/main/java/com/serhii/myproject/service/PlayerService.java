package com.serhii.myproject.service;


import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;

import java.util.List;

public interface PlayerService {
    Player create(Player player);
    Player readById(long id);
    Player update(Player player);
    void delete(long id);

    List<Player> getByPosition(PlayerPosition position);
    List<Player> getAllPlayers();

}
