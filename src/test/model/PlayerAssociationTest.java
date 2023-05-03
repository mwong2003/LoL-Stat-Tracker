package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerAssociationTest {
    List<Player> allPlayers;

    @BeforeEach
    void setup() {
        allPlayers = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        List<Player> players = new ArrayList<>();
        PlayerAssociation playerAssociation = new PlayerAssociation(players);
        assertEquals(0, playerAssociation.getAllPlayers().size());
    }
}
