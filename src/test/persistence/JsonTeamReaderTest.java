package persistence;

import model.League;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTeamReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonTeamReader reader = new JsonTeamReader(".data/teamDoesNotExist.json");
        try {
            League league = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlayer() {
        JsonTeamReader reader = new JsonTeamReader("./data/testReaderEmptyTeam.json");
        try {
            League league = reader.read();
            assertEquals(0, league.getAllTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayer() {
        JsonTeamReader reader = new JsonTeamReader("./data/testReaderGeneralTeam.json");
        try {
            League league = reader.read();
            assertEquals(1, league.getAllTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
