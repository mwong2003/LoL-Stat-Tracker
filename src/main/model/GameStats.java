package model;

import org.json.JSONObject;

// Represents stats of one game with amount of kills, deaths, assists, creep score, damage dealt, and game end time
public class GameStats {

    private int kills;
    private int deaths;
    private int assists;
    private int cs;
    private int dmg;
    private int gold;
    private String gameTime;


    /*  REQUIRES: gameTime must be in the form "XX:YY" or "X:YY" where X is minutes and Y is seconds
     *  EFFECTS: Creates kill, death, assist, creep score, damage, gold, and end gametime
     *          stats for a player in a single game
     */
    public GameStats(int kills, int deaths, int assists, int cs, int dmg, int gold, String gameTime) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.cs = cs;
        this.dmg = dmg;
        this.gold = gold;
        this.gameTime = gameTime;
        EventLog.getInstance().logEvent(new Event("Game stat created"));
    }

    // EFFECTS: Converts getGameTime() "XX:YY" to minutes where XX is minutes and YY is seconds
    public double gameTimeToMinutes() {
        String gameTime = getGameTime();
        int indexColon = gameTime.indexOf(":");
        String minutesString;
        String secondsString;
        if (indexColon != -1) {
            minutesString = gameTime.substring(0, indexColon);
            secondsString = gameTime.substring(indexColon + 1);
        } else {
            return 0;
        }

        int minutesInt = Integer.parseInt(minutesString);
        int secondsInt = Integer.parseInt(secondsString);
        return minutesInt + (secondsInt / 60.0);
    }

    /* EFFECTS: Returns (getKills() + getAssists()) / getDeaths() to one decimal place as String
     *          if getDeaths() == 0, return "Perfect KDA"
     */
    public String getKdaAverage() {
        if (getDeaths() != 0) {
            return Double.toString((Math.round(((getKills() + getAssists()) / (double) getDeaths()) * 10.0)) / 10.0);
        }
        return "Perfect KDA";
    }

    // EFFECTS: Returns cs gained per minute rounded to one decimal place
    public double getCsPerMinute() {
        return Math.round((getCs() / gameTimeToMinutes()) * 10.0) / 10.0;
    }

    // EFFECTS: Returns damage dealt per minute rounded to nearest whole number
    public int getDamagePerMinute() {
        return (int) Math.round((getDmg() / gameTimeToMinutes()));
    }

    // EFFECTS: Returns gold gained per minute to nearest whole number
    public int getGoldPerMinute() {
        return (int) Math.round((getGold() / gameTimeToMinutes()));
    }

    // EFFECTS: Converts GameStats to JSONObject with kills, deaths, assists, cs, damage, gold, and game time
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Kills", kills);
        json.put("Deaths", deaths);
        json.put("Assists", assists);
        json.put("CS", cs);
        json.put("Damage", dmg);
        json.put("Gold", gold);
        json.put("Game time", gameTime);
        return json;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }
}
