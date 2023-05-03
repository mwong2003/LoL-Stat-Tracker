package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Represents a team having name, 5 players: top, jungle, mid, bot, supp, and those 5 players in a list
public class Team {

    private String teamName;
    private List<Player> players;
    private Player top;
    private Player jg;
    private Player mid;
    private Player bot;
    private Player supp;

    // EFFECTS: Creates a team with team name and five players (top, jungle, mid, bot, support) with team gold
    public Team(String teamName, List<Player> players, Player top, Player jg, Player mid, Player bot, Player supp) {
        this.teamName = teamName;
        this.players = players;
        this.top = top;
        this.jg = jg;
        this.mid = mid;
        this.bot = bot;
        this.supp = supp;
        players.add(top);
        players.add(jg);
        players.add(mid);
        players.add(bot);
        players.add(supp);
        EventLog.getInstance().logEvent(new Event("Team " + teamName + " created"));
    }

    // REQUIRES: role must be one of "Top", "Jg", "Mid", "Bot", or "Supp"
    // EFFECTS: Returns the average damage percentage role deals in games
    public String getDmgPercentFromRole(String role) {
        double dmg;
        if (role.equals("Top")) {
            dmg = players.get(0).getAverageDmgPerMin();
        } else if (role.equals("Jg")) {
            dmg = players.get(1).getAverageDmgPerMin();
        } else if (role.equals("Mid")) {
            dmg = players.get(2).getAverageDmgPerMin();
        } else if (role.equals("Bot")) {
            dmg = players.get(3).getAverageDmgPerMin();
        } else {
            dmg = players.get(4).getAverageDmgPerMin();
        }
        double dmgShare = Math.round((dmg / getTotalTeamDamagePerMin()) * 1000.0) / 1000.0;
        return Double.toString(Math.round((dmgShare * 100) * 10.0) / 10.0) + "%";
    }

    // EFFECTS: Returns an average of total damage per minute team deals
    public double getTotalTeamDamagePerMin() {
        double totalDmgPerMin = 0;
        for (Player player : players) {
            totalDmgPerMin += player.getAverageDmgPerMin();
        }
        return totalDmgPerMin;
    }

    // REQUIRES: role must be one of "Top", "Jg", "Mid", "Bot", or "Supp"
    // EFFECTS: Returns the average gold percentage role earns in games
    public String getGoldPercentFromRole(String role) {
        double gold;
        if (role.equals("Top")) {
            gold = players.get(0).getAverageGoldPerMin();
        } else if (role.equals("Jg")) {
            gold = players.get(1).getAverageGoldPerMin();
        } else if (role.equals("Mid")) {
            gold = players.get(2).getAverageGoldPerMin();
        } else if (role.equals("Bot")) {
            gold = players.get(3).getAverageGoldPerMin();
        } else {
            gold = players.get(4).getAverageGoldPerMin();
        }
        double goldShare = Math.round((gold / getTotalTeamGoldPerMin()) * 1000.0) / 1000.0;
        return Double.toString(Math.round((goldShare * 100) * 10.0) / 10.0) + "%";
    }

    // EFFECTS: Returns an average of total gold per minute team earns
    public double getTotalTeamGoldPerMin() {
        double totalGoldPerMin = 0;
        for (Player player : players) {
            totalGoldPerMin += player.getAverageGoldPerMin();
        }
        return totalGoldPerMin;
    }

    // EFFECTS: Converts team to JSONObject with a team name and list of players
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Team Name", teamName);
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: Converts player list to JSONArray
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put((player.toJson()));
        }
        return jsonArray;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getTop() {
        return top;
    }

    public void setTop(Player top) {
        this.top = top;
    }

    public Player getJg() {
        return jg;
    }

    public void setJg(Player jg) {
        this.jg = jg;
    }

    public Player getMid() {
        return mid;
    }

    public void setMid(Player mid) {
        this.mid = mid;
    }

    public Player getBot() {
        return bot;
    }

    public void setBot(Player bot) {
        this.bot = bot;
    }

    public Player getSupp() {
        return supp;
    }

    public void setSupp(Player supp) {
        this.supp = supp;
    }


}
