package com.serhii.myproject.service;


import com.serhii.myproject.model.Player;

import java.util.List;

public interface PlayerService {
    Player create(Player player);
    Player readById(long id);
    Player update(Player player);
    void delete(long id);

    List<Player> getByPositionId();
    List<Player> getAllPlayers();
}
