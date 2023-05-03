package persistence;

import model.GameStats;
import model.League;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Citation: Modeled JsonTeamReader, read, readFile, parseLeague, from provided JsonSerializationDemo project
// Represents a reader that reads and returns information from JSON file about Teams
public class JsonTeamReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonTeamReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads league from file and returns it;
    // throws IOException if an error occurs reading data from file
    public League read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        JSONArray teamList = jsonObject.getJSONArray("Teams");
        ArrayList<Team> arrayTeamList = jsonTeamArrayToArrayList(teamList);
        League league = new League(arrayTeamList);
        return league;
    }

    // EFFECTS: Converts a jsonArray of Team to an ArrayList and returns the ArrayList
    private ArrayList<Team> jsonTeamArrayToArrayList(JSONArray jsonArray) {
        ArrayList<Team> teamList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Team team = jsonToTeam(json);
            teamList.add(team);
        }
        return teamList;
    }

    // EFFECTS: Reads json file of team and creates a team from it and returns it
    private Team jsonToTeam(JSONObject json) {
        String teamName = json.getString("Team Name");
        JSONArray jsonArray = json.getJSONArray("Players");
        ArrayList<Player> players = jsonPlayerArrayToArrayList(jsonArray);
        Player top = players.get(0);
        Player jg = players.get(1);
        Player mid = players.get(2);
        Player bot = players.get(3);
        Player supp = players.get(4);
        Team team = new Team(teamName, new ArrayList<Player>(), top, jg, mid, bot, supp);
        return team;
    }

    // EFFECTS: Converts jsonArray of Player to an ArrayList and returns the ArrayList
    private ArrayList<Player> jsonPlayerArrayToArrayList(JSONArray jsonArray) {
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonPlayer = jsonArray.getJSONObject(i);
            String username = jsonPlayer.getString("Username");
            JSONArray jsonStats = jsonPlayer.getJSONArray("Game stats");
            ArrayList<GameStats> stats = jsonStatsArrayToArrayList(jsonStats);
            Player player = new Player(username, stats);
            players.add(player);
        }
        return players;
    }

    // EFFECTS: Converts jsonArray of GameStats to an ArrayList and returns the ArrayList
    private ArrayList<GameStats> jsonStatsArrayToArrayList(JSONArray jsonArray) {
        ArrayList<GameStats> stats = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject gameStat = jsonArray.getJSONObject(i);
            int kills = gameStat.getInt("Kills");
            int deaths = gameStat.getInt("Deaths");
            int assists = gameStat.getInt("Assists");
            int cs = gameStat.getInt("CS");
            int dmg = gameStat.getInt("Damage");
            int gold = gameStat.getInt("Gold");
            String gameTime = gameStat.getString("Game time");
            GameStats stat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            stats.add(stat);
        }
        return stats;
    }
}
