package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeagueTest {
    private List<Team> allTeams;

    @BeforeEach
    void setup() {
        allTeams = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        List<Team> teams = new ArrayList<>();
        League league = new League(teams);
        assertEquals(0, league.getAllTeams().size());
    }
}
