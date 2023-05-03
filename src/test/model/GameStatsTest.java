package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStatsTest {

    private GameStats stats;

    @BeforeEach
    void setup() {
        stats = new GameStats(10, 2, 12, 200, 10000, 17000, "33:18");
    }

    @Test
    void testConstructor() {
        assertEquals(10, stats.getKills());
        assertEquals(2, stats.getDeaths());
        assertEquals(12, stats.getAssists());
        assertEquals(200, stats.getCs());
        assertEquals(10000, stats.getDmg());
        assertEquals(17000, stats.getGold());
        assertEquals("33:18", stats.getGameTime());
    }

    @Test
    void testGameTimeToMinutesMoreThanTenMins() {
        assertEquals(33.3, stats.gameTimeToMinutes());
    }

    @Test
    void testGameTimeToMinutesLessThanTenMins() {
        stats.setGameTime("9:15");
        assertEquals(9.25, stats.gameTimeToMinutes());
    }

    @Test
    void testGameTimeToMinutesMinsZero() {
        stats.setGameTime("0:12");
        assertEquals(0.20, stats.gameTimeToMinutes());
    }

    @Test
    void testGameTimeToMinutesZero() {
        stats.setGameTime("0:00");
        assertEquals(0.00, stats.gameTimeToMinutes());
    }

    @Test
    void testInvalidGameTimeToMinutes() {
        stats.setGameTime("");
        assertEquals(0.00, stats.gameTimeToMinutes());
    }

    @Test
    void testGetKdaAverageDeathsNotZero() {
        assertEquals("11.0", stats.getKdaAverage());
    }

    @Test
    void testGetKdaAverageDecimalNumber() {
        stats.setDeaths(3);
        assertEquals("7.3", stats.getKdaAverage());
    }

    @Test
    void testGetKdaAverageZero() {
        stats.setKills(0);
        stats.setAssists(0);
        assertEquals("0.0", stats.getKdaAverage());
    }

    @Test
    void testGetKdaAverageDeathsIsZero() {
        stats.setDeaths(0);
        assertEquals("Perfect KDA", stats.getKdaAverage());
    }

    @Test
    void testCsPerMinute() {
        assertEquals(6.0, stats.getCsPerMinute());
    }

    @Test
    void testCsPerMinuteZero() {
        stats.setCs(0);
        assertEquals(0.0, stats.getCsPerMinute());
    }

    @Test
    void testGetDamagePerMinute() {
        assertEquals(300, stats.getDamagePerMinute());
    }

    @Test
    void testGetDamagePerMinuteZero() {
        stats.setDmg(0);
        assertEquals(0.0, stats.getDamagePerMinute());
    }

    @Test
    void testGetGoldPerMinute() {
        assertEquals(511, stats.getGoldPerMinute());
    }

    @Test
    void testGetGoldPerMinuteZero() {
        stats.setGold(0);
        assertEquals(0.0, stats.getGoldPerMinute());
    }

}
