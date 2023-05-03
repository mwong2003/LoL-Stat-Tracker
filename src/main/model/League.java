package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents teams that are competing against each other inside a list
public class League {
    private List<Team> allTeams;
    
    // EFFECTS: Creates a league of all teams created
    public League(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    // EFFECTS: Converts League to JSONObject with list of all teams created
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Teams", teamsToJson());
        return json;
    }

    // EFFECTS: Converts getAllTeams() to JSONArray
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Team team : allTeams) {
            jsonArray.put(team.toJson());
        }
        return jsonArray;
    }

    public void removeTeam(String teamName) {
        Team removedTeam = null;
        for (int i = 0; i < allTeams.size(); i++) {
            if (allTeams.get(i).getTeamName().equals(teamName)) {
                removedTeam = allTeams.remove(i);
            }
        }
        if (removedTeam == null) {
            EventLog.getInstance().logEvent(new Event(teamName + " does not exist to remove"));
        } else {
            EventLog.getInstance().logEvent(new Event(teamName + " has been removed"));
        }
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }
}
