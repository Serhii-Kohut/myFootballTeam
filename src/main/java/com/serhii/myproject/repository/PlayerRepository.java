package com.serhii.myproject.repository;

import com.serhii.myproject.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findPlayersByPositionId();
}
