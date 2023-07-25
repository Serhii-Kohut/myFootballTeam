package com.serhii.myproject.service.impl;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;
import com.serhii.myproject.repository.PlayerRepository;
import com.serhii.myproject.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player readById(long id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Override
    public Player update(Player player) {
        Player oldPlayer = readById(player.getId());
        if (oldPlayer != null) {
            oldPlayer.setPosition(player.getPosition());
            return playerRepository.save(oldPlayer);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> getByPosition(PlayerPosition position) {
        return playerRepository.findPlayersByPosition(position);
    }


    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.isEmpty() ? new ArrayList<>() : players;
    }

}
