package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Citation: Modeled JsonPlayerReader, read, readFile, parsePlayerAssociation from provided
// JsonSerializationDemo project
// Represents a reader that reads and returns information from JSON file about Players
public class JsonPlayerReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonPlayerReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerAssociation read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerAssociation(jsonObject);
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
    private PlayerAssociation parsePlayerAssociation(JSONObject jsonObject) {
        JSONArray playerList = jsonObject.getJSONArray("Players");
        ArrayList<Player> arrayPlayerList = jsonPlayerArrayToArrayList(playerList);
        PlayerAssociation playerAssociation = new PlayerAssociation(arrayPlayerList);
        return playerAssociation;
    }

    // EFFECTS: Converts jsonArray of Player to ArrayList and returns the ArrayList
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

    // EFFECTS: // EFFECTS: Converts jsonArray of GameStats to ArrayList and returns the ArrayList
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
