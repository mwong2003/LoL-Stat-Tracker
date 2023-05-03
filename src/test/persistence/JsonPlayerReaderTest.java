package persistence;

import model.PlayerAssociation;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonPlayerReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonPlayerReader reader = new JsonPlayerReader(".data/playerDoesNotExist.json");
        try {
            PlayerAssociation playerAssociation = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlayer() {
        JsonPlayerReader reader = new JsonPlayerReader("./data/testReaderEmptyPlayer.json");
        try {
            PlayerAssociation playerAssociation = reader.read();
            assertEquals(0, playerAssociation.getAllPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayer() {
        JsonPlayerReader reader = new JsonPlayerReader("./data/testReaderGeneralPlayer.json");
        try {
            PlayerAssociation playerAssociation = reader.read();
            assertEquals(3, playerAssociation.getAllPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
