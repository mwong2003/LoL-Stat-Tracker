package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {

    private Team t1;
    private List<Player> players;

    private List<GameStats> topStatList;
    private GameStats topGame1;
    private GameStats topGame2;
    private GameStats topGame3;

    private List<GameStats> jgStatList;
    private GameStats jgGame1;
    private GameStats jgGame2;
    private GameStats jgGame3;

    private List<GameStats> midStatList;
    private GameStats midGame1;
    private GameStats midGame2;
    private GameStats midGame3;

    private List<GameStats> botStatList;
    private GameStats botGame1;
    private GameStats botGame2;
    private GameStats botGame3;

    private List<GameStats> suppStatList;
    private GameStats suppGame1;
    private GameStats suppGame2;
    private GameStats suppGame3;

    private Player top;
    private Player jg;
    private Player mid;
    private Player bot;
    private Player supp;

    @BeforeEach
    void setup() {
        String game1Time = "29:03";
        String game2Time = "34:50";
        String game3Time = "30:30";

        topGame1 = new GameStats(1, 2, 5, 237, 9031, 10869, game1Time);
        topGame2 = new GameStats(2, 5, 10, 174, 14746, 10661, game2Time);
        topGame3 = new GameStats(0, 5, 5, 159, 11792, 8174, game3Time);
        topStatList = new ArrayList<>(Arrays.asList(topGame1, topGame2, topGame3));
        top = new Player("Zeus", topStatList);

        jgGame1 = new GameStats(3, 1, 9, 186, 6346, 10685, game1Time);
        jgGame2 = new GameStats(9, 3, 6, 213, 17288, 15209, game2Time);
        jgGame3 = new GameStats(2, 6, 3, 146, 6061, 8696, game3Time);
        jgStatList = new ArrayList<>(Arrays.asList(jgGame1, jgGame2, jgGame3));
        jg = new Player("Oner", jgStatList);

        midGame1 = new GameStats(5, 1, 7, 286, 26926, 15180, game1Time);
        midGame2 = new GameStats(3, 2, 15, 308, 23757, 15738, game2Time);
        midGame3 = new GameStats(2, 2, 2, 284, 17059, 12498, game3Time);
        midStatList = new ArrayList<>(Arrays.asList(midGame1, midGame2, midGame3));
        mid = new Player("Faker", midStatList);

        botGame1 = new GameStats(7, 3, 5, 286, 18885, 14610, game1Time);
        botGame2 = new GameStats(6, 2, 9, 285, 29176, 17187, game2Time);
        botGame3 = new GameStats(1, 2, 3, 380, 9357, 13805, game3Time);
        botStatList = new ArrayList<>(Arrays.asList(botGame1, botGame2, botGame3));
        bot = new Player("Gumayusi", botStatList);

        suppGame1 = new GameStats(0, 2, 12, 35, 3774, 7344, game1Time);
        suppGame2 = new GameStats(0, 3, 16, 24, 8122, 8568, game2Time);
        suppGame3 = new GameStats(0, 2, 5, 41, 2139, 6074, game3Time);
        suppStatList = new ArrayList<>(Arrays.asList(suppGame1, suppGame2, suppGame3));
        supp = new Player("Keria", suppStatList);

        players = new ArrayList<>();
        t1 = new Team("T1", players, top, jg, mid, bot, supp);

    }

    @Test
    void testConstructor() {
        assertEquals("T1", t1.getTeamName());
        assertEquals(players, t1.getPlayers());
        assertEquals(top, t1.getTop());
        assertEquals(jg, t1.getJg());
        assertEquals(mid, t1.getMid());
        assertEquals(bot, t1.getBot());
        assertEquals(supp, t1.getSupp());
    }

    @Test
    void testConstructorSwapRoles() {
        t1.setTeamName("FNC");
        t1.setPlayers(new ArrayList<>());
        t1.setTop(jg);
        t1.setJg(mid);
        t1.setMid(bot);
        t1.setBot(supp);
        t1.setSupp(top);
        assertEquals("FNC", t1.getTeamName());
        assertEquals(0, t1.getPlayers().size());
        assertEquals(jg, t1.getTop());
        assertEquals(mid, t1.getJg());
        assertEquals(bot, t1.getMid());
        assertEquals(supp, t1.getBot());
        assertEquals(top, t1.getSupp());
    }

    @Test
    void testTopDamagePercent() {
        assertEquals("17.5%", t1.getDmgPercentFromRole("Top"));
    }

    @Test
    void testJgDamagePercent() {
        assertEquals("14.2%", t1.getDmgPercentFromRole("Jg"));
    }

    @Test
    void testMidDamagePercent() {
        assertEquals("33.7%", t1.getDmgPercentFromRole("Mid"));
    }

    @Test
    void testBotDamagePercent() {
        assertEquals("27.9%", t1.getDmgPercentFromRole("Bot"));
    }

    @Test
    void testSuppDamagePercent() {
        assertEquals("6.7%", t1.getDmgPercentFromRole("Supp"));
    }

    @Test
    void testTotalTeamDmgPerMin() {
        assertEquals(2143.0, t1.getTotalTeamDamagePerMin());
    }

    @Test
    void testTopGoldPercent() {
        assertEquals("17.0%", t1.getGoldPercentFromRole("Top"));
    }

    @Test
    void testJgGoldPercent() {
        assertEquals("19.5%", t1.getGoldPercentFromRole("Jg"));
    }

    @Test
    void testMidGoldPercent() {
        assertEquals("24.9%", t1.getGoldPercentFromRole("Mid"));
    }

    @Test
    void testBotGoldPercent() {
        assertEquals("26.0%", t1.getGoldPercentFromRole("Bot"));
    }

    @Test
    void testSuppGoldPercent() {
        assertEquals("12.5%", t1.getGoldPercentFromRole("Supp"));
    }

    @Test
    void testTotalGoldPerMin() {
        assertEquals(1857.0, t1.getTotalTeamGoldPerMin());
    }
}
