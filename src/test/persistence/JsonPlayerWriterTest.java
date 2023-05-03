package persistence;

import model.GameStats;
import model.Player;
import model.PlayerAssociation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonPlayerWriterTest {

    @Test
    void testWriterInvalidPlayer() {
        try {
            JsonPlayerWriter writer = new JsonPlayerWriter("./data/\0doesNotExist.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlayer() {
        try {
            PlayerAssociation pa = new PlayerAssociation(new ArrayList<>());
            JsonPlayerWriter writer = new JsonPlayerWriter("./data/testWriterEmptyPlayer.json");
            writer.open();
            writer.write(pa);
            writer.close();

            JsonPlayerReader reader = new JsonPlayerReader("./data/testWriterEmptyPlayer.json");
            pa = reader.read();
            assertEquals(0, pa.getAllPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayer() {
        try {
            GameStats game1 = new GameStats(5, 1, 7, 286, 26926, 15180, "29:03");
            List<GameStats> stats = new ArrayList<>(Arrays.asList(game1));
            Player player = new Player("Player 1", stats);
            List<Player> players = new ArrayList<>();
            players.add(player);
            PlayerAssociation pa = new PlayerAssociation(players);
            JsonPlayerWriter writer = new JsonPlayerWriter("./data/testWriterGeneralPlayer.json");
            writer.open();
            writer.write(pa);
            writer.close();

            JsonPlayerReader reader = new JsonPlayerReader("./data/testWriterGeneralPlayer.json");
            pa = reader.read();
            assertEquals(1, pa.getAllPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
