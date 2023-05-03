package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Represents a player having a username and a list of stats with size being number of games played by player
public class Player {

    private String username;
    private List<GameStats> statList;

    // EFFECTS: Creates player with username and a list of stats from one or more games
    public Player(String username, List<GameStats> statList) {
        this.username = username;
        this.statList = statList;
        EventLog.getInstance().logEvent(new Event("Player " + username + " created"));
    }

    // EFFECTS: Gets the average amount of kills within getStatList()
    public double getAverageKills() {
        double totalKills = 0;
        for (GameStats stats : statList) {
            totalKills += stats.getKills();
        }
        return Math.round((totalKills / statList.size()) * 10.0) / 10.0;
    }

    // EFFECTS: Gets the average amount of deaths within getStatList()
    public double getAverageDeaths() {
        double totalDeaths = 0;
        for (GameStats stats : statList) {
            totalDeaths += stats.getDeaths();
        }
        return Math.round((totalDeaths / statList.size()) * 10.0) / 10.0;
    }

    // EFFECTS: Gets the average amount of assists within getStatList()
    public double getAverageAssists() {
        double totalAssists = 0;
        for (GameStats stats : statList) {
            totalAssists += stats.getAssists();
        }
        return Math.round((totalAssists / statList.size()) * 10.0) / 10.0;
    }

    // EFFECTS: Gets the average KDA (kills + assists)/deaths
    //          Returns "Perfect KDA" if deaths are 0
    public String getAverageKDA() {
        if (getAverageDeaths() != 0) {
            double averageKDA = (getAverageKills() + getAverageAssists()) / getAverageDeaths();
            return Double.toString(Math.round(averageKDA * 10.0) / 10.0);
        }
        return "Perfect KDA";

    }

    // EFFECTS: Gets the average amount of creep score per min within getStatList()
    public double getAverageCsPerMin() {
        double totalCsPerMin = 0;
        for (GameStats stats : statList) {
            totalCsPerMin += stats.getCsPerMinute();
        }
        return Math.round((totalCsPerMin / statList.size()) * 10.0) / 10.0;
    }

    // EFFECTS: Gets the average amount of damage per min within getStatList()
    public double getAverageDmgPerMin() {
        int totalDmgPerMin = 0;
        for (GameStats stats : statList) {
            totalDmgPerMin += stats.getDamagePerMinute();
        }
        return Math.round((totalDmgPerMin / (double) statList.size()));
    }

    // EFFECTS: Gets the average gold earned within getStatList()
    public double getAverageGoldPerMin() {
        int totalGoldPerMin = 0;
        for (GameStats stats : statList) {
            totalGoldPerMin += stats.getGoldPerMinute();
        }
        return Math.round((totalGoldPerMin / (double) statList.size()));
    }

    // EFFECTS: Gets the average game time within getStatList()
    public String getAverageGameTime() {
        double totalGameTime = 0;
        for (GameStats stats : statList) {
            totalGameTime += stats.gameTimeToMinutes();
        }
        return convertDecimalTimeToClockTime(totalGameTime / getStatList().size());
    }

    // REQUIRES: decimalTime >= 0
    // EFFECTS: Converts time in decimal to a clock time like "XX:YY" where XX is mins and YY is seconds
    public String convertDecimalTimeToClockTime(double decimalTime) {
        int minutes = (int) decimalTime;
        double secondsInDecimal = decimalTime - minutes;
        int secondsInClock = (int) Math.round(secondsInDecimal * 60);
        if (secondsInClock < 10) {
            return Integer.toString(minutes) + ":0" + Integer.toString(secondsInClock);
        }
        return Integer.toString(minutes) + ":" + Integer.toString(secondsInClock);
    }

    // EFFECTS: Converts player to JSONObject with username and list of stats
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Username", username);
        json.put("Game stats", statListToJson());
        return json;
    }

    // EFFECTS: Converts stat list of player to JSONArray
    public JSONArray statListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (GameStats stats : statList) {
            jsonArray.put(stats.toJson());
        }
        return jsonArray;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GameStats> getStatList() {
        return statList;
    }

    public void setStatList(List<GameStats> statList) {
        this.statList = statList;
    }


}
