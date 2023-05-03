package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Represents an association of all players that have been created through a list
public class PlayerAssociation {
    private List<Player> allPlayers;

    // EFFECTS: Creates a player association that contains all players created
    public PlayerAssociation(List<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    // EFFECTS: Converts Player Association to JSONObject containing a list of all players created
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: Converts getAllPlayers() to JSONArray
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player player : allPlayers) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }
}
