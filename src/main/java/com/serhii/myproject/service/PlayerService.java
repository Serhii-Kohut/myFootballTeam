package com.serhii.myproject.service;


import java.util.List;

public interface PlayerService {
    PlayerService create(PlayerService player);
    PlayerService readById(long id);
    PlayerService update(PlayerService player);
    void delete(long id);

    List<PlayerService> getByPositionId();
    List<PlayerService> getAllPlayers();
}
