package ui.panels;

import model.League;
import persistence.JsonTeamWriter;

import java.io.FileNotFoundException;

// Represents the task to save all teams to JSON file
public class SaveTeam {
    private JsonTeamWriter writer;
    private static final String JSON_TEAM_LOCATION = "./data/team.json";

    // EFFECTS: Generates writer that writes all teams to JSON file
    public SaveTeam() {
        writer = new JsonTeamWriter(JSON_TEAM_LOCATION);
    }

    // EFFECTS: Saves all teams in league to JSON file
    public boolean saveTeams(League league) {
        try {
            writer.open();
            writer.write(league);
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
