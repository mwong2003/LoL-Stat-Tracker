package persistence;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTeamWriterTest {

    @Test
    void testWriterInvalidTeam() {
        try {
            JsonTeamWriter writer = new JsonTeamWriter("./data/\0doesNotExist.json");
            writer.open();
            Assertions.fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            League league = new League(new ArrayList<>());
            JsonTeamWriter writer = new JsonTeamWriter("./data/testWriterEmptyTeam.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonTeamReader reader = new JsonTeamReader("./data/testWriterEmptyTeam.json");
            league = reader.read();
            assertEquals(0, league.getAllTeams().size());
        } catch (IOException e) {
            Assertions.fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlayer() {
        try {
            GameStats topGame = new GameStats(5,5,5,5,5,5,"10:00");
            GameStats jgGame = new GameStats(5,5,5,5,5,5,"10:00");
            GameStats midGame = new GameStats(5,5,5,5,5,5,"10:00");
            GameStats botGame = new GameStats(5,5,5,5,5,5,"10:00");
            GameStats suppGame = new GameStats(5,5,5,5,5,5,"10:00");
            Player top = new Player("Fudge", new ArrayList<GameStats>(Arrays.asList(topGame)));
            Player jg = new Player("Blaber", new ArrayList<GameStats>(Arrays.asList(jgGame)));
            Player mid = new Player("Jensen", new ArrayList<GameStats>(Arrays.asList(midGame)));
            Player bot = new Player("Berserker", new ArrayList<GameStats>(Arrays.asList(botGame)));
            Player supp = new Player("Zven", new ArrayList<GameStats>(Arrays.asList(suppGame)));
            Team team = new Team("Cloud9", new ArrayList<>(), top, jg, mid, bot, supp);
            League league = new League(new ArrayList<Team>(Arrays.asList(team)));
            JsonTeamWriter writer = new JsonTeamWriter("./data/testWriterGeneralTeam.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonTeamReader reader = new JsonTeamReader("./data/testWriterGeneralTeam.json");
            league = reader.read();
            assertEquals(1, league.getAllTeams().size());
        } catch (IOException e) {
            Assertions.fail("Exception should not have been thrown");
        }
    }
}
