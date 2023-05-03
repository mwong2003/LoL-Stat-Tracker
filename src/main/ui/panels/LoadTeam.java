package ui.panels;

import model.League;
import model.Team;
import persistence.JsonTeamReader;

import java.io.IOException;
import java.util.List;

// Represents the task to load all teams saved to JSON
public class LoadTeam {
    private List<Team> allTeams;
    private JsonTeamReader reader;
    private static final String JSON_TEAM_LOCATION = "./data/team.json";

    // EFFECTS: Generates a reader that reads teams in JSON file
    public LoadTeam(List<Team> allTeams) {
        this.allTeams = allTeams;
        reader = new JsonTeamReader(JSON_TEAM_LOCATION);
    }

    // EFFECTS: Loads all teams from JSON file to allTeams
    public boolean loadTeams() {
        try {
            League league = reader.read();
            List<Team> teams = league.getAllTeams();
            allTeams.addAll(teams);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }
}
