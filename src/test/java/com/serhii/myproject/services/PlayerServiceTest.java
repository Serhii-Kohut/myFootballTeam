package com.serhii.myproject.services;

import com.serhii.myproject.model.Player;
import com.serhii.myproject.model.PlayerPosition;
import com.serhii.myproject.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PlayerServiceTest {
    @Autowired
    private PlayerService playerService;

    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        player1 = new Player();
        player1.setPlayerFirstName("Cris");
        player1.setPlayerLastName("Pulisic");
        player1.setPosition(PlayerPosition.MIDFIELDER);
        player1.setDateOfBirth(LocalDate.of(1999, 6, 11));
        player1.setJerseyNumber(17L);
        player1.setMarketValue(25000000L);

        playerService.create(player1);

    }

    @Test
    public void testCreatePlayer() {
        Player createdPlayer = playerService.create(player1);

        assertNotNull(createdPlayer.getId());
        assertEquals("Cris", createdPlayer.getPlayerFirstName());
        assertEquals(Optional.of(25000000L), Optional.ofNullable(createdPlayer.getMarketValue()));
        assertEquals(PlayerPosition.MIDFIELDER, createdPlayer.getPosition());
    }

    @Test
    public void testReadPlayer() {
        Player createdPlayer = playerService.create(player1);

        Player playerById = playerService.readById(createdPlayer.getId());
        assertNotNull(playerById.getId());

        assertEquals("Cris", createdPlayer.getPlayerFirstName());
        assertEquals(Optional.of(25000000L), Optional.ofNullable(createdPlayer.getMarketValue()));
        assertEquals(PlayerPosition.MIDFIELDER, createdPlayer.getPosition());

    }

    @Test
    public void testUpdatePlayer() {
        Player createdPlayer = playerService.create(player1);
        Player updatedPlayer = playerService.update(createdPlayer);

        assertEquals(player1.getId(), updatedPlayer.getId());
        assertEquals(PlayerPosition.MIDFIELDER, createdPlayer.getPosition());
        assertEquals(player1.getPlayerFirstName(), updatedPlayer.getPlayerFirstName());

    }

    @Test
    public void testDeletePlayer() {
        playerService.create(player1);
        List<Player> playerList = playerService.getAllPlayers();

        assertTrue(playerList.contains(player1));

        playerService.delete(player1.getId());
        playerList = playerService.getAllPlayers();
        assertFalse(playerList.contains(player1));
    }

}
