package com.serhii.myproject.repository;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByPosition(PlayerPosition position);
}
