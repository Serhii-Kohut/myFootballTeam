package com.serhii.myproject.service;


import java.util.List;

public interface Player {
    Player create(Player player);
    Player readById(long id);
    Player update(Player player);
    void delete(long id);

    List<Player> getByPositionId();
    List<Player> getAllPlayers();
}
