package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player player;
    List<GameStats> stats;
    GameStats game1;
    GameStats game2;
    GameStats game3;

    @BeforeEach
    void setup() {
        //https://gol.gg/game/stats/42909/page-game/
        game1 = new GameStats(5, 1, 7, 286, 26926, 15180, "29:03");
        //https://gol.gg/game/stats/42911/page-fullstats/
        game2 = new GameStats(3, 2, 15, 308, 23757, 15738, "34:50");
        //https://gol.gg/game/stats/43255/page-game/
        game3 = new GameStats(2, 2, 2, 284, 17059, 12498, "30:30");
        stats = new ArrayList<>(Arrays.asList(game1, game2, game3));
        player = new Player("Faker", stats);
    }

    @Test
    void testConstructor() {
        assertEquals("Faker", player.getUsername());
        assertEquals(3, player.getStatList().size());
    }

    @Test
    void testConstructorEmptyStatList() {
        player.setUsername("N/A");
        player.setStatList(new ArrayList<>());
        assertEquals("N/A", player.getUsername());
        assertEquals(0, player.getStatList().size());
    }

    @Test
    void testGetAverageKills() {
        assertEquals(3.3, player.getAverageKills());
    }

    @Test
    void testGetAverageDeaths() {
        assertEquals(1.7, player.getAverageDeaths());
    }

    @Test
    void testGetAverageAssists() {
        assertEquals(8.0, player.getAverageAssists());
    }

    @Test
    void testGetAverageKDADeathsNotZero() {
        assertEquals("6.6", player.getAverageKDA());
    }

    @Test
    void testGetAverageKDADeathsZero() {
        GameStats game = new GameStats(3, 0, 2, 123, 5215, 2166, "10:00");
        ArrayList<GameStats> stats = new ArrayList<>(Arrays.asList(game));
        Player player = new Player("Fake Name", stats);
        assertEquals("Perfect KDA", player.getAverageKDA());
    }

    @Test
    void testGetAverageCsPerMin() {
        assertEquals(9.3, player.getAverageCsPerMin());
    }

    @Test
    void testGetAverageDmgPerMin() {
        assertEquals(723.0, player.getAverageDmgPerMin());
    }

    @Test
    void testGetAverageGoldPerMin() {
        assertEquals(462, player.getAverageGoldPerMin());
    }

    @Test
    void testGetAverageGameTime() {
        assertEquals("31:28", player.getAverageGameTime());
    }

    @Test
    void testConvertDecimalTimeToClockTime() {
        double decimalTime = 33.3;
        assertEquals("33:18", player.convertDecimalTimeToClockTime(decimalTime));
    }

    @Test
    void testConvertDecimalTimeToClockTimeSecondsLessThanTen() {
        double decimalTime = 25.1;
        assertEquals("25:06", player.convertDecimalTimeToClockTime(decimalTime));
    }

    @Test
    void testConvertDecimalTimeToClockTimeMinutesLessThanTen() {
        double decimalTime = 9.3;
        assertEquals("9:18", player.convertDecimalTimeToClockTime(decimalTime));
    }

    @Test
    void testConvertDecimalTimeToClockTimeMinutesLessThanOne() {
        double decimalTime = 0.3;
        assertEquals("0:18", player.convertDecimalTimeToClockTime(decimalTime));
    }

}
