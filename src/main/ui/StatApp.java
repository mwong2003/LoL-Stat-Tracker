package ui;

import model.*;
import persistence.JsonPlayerReader;
import persistence.JsonTeamReader;
import persistence.JsonPlayerWriter;
import persistence.JsonTeamWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StatApp {
    private static final String JSON_TEAM_LOCATION = "./data/team.json";
    private static final String JSON_PLAYER_LOCATION = "./data/player.json";
    private JsonTeamWriter jsonTeamWriter;
    private JsonTeamReader jsonTeamReader;
    private JsonPlayerWriter jsonPlayerWriter;
    private JsonPlayerReader jsonPlayerReader;
    private Scanner input = new Scanner(System.in);
    private List<Player> allPlayers = new ArrayList<>();
    private List<Team> allTeams = new ArrayList<>();
    private League league;
    private PlayerAssociation playerAssociation;

    public StatApp() {
        runStat();
    }

    // EFFECTS: Runs input menus depending on displayMenu() input
    private void runStat() {
        jsonTeamWriter = new JsonTeamWriter(JSON_TEAM_LOCATION);
        jsonTeamReader = new JsonTeamReader(JSON_TEAM_LOCATION);
        jsonPlayerWriter = new JsonPlayerWriter(JSON_PLAYER_LOCATION);
        jsonPlayerReader = new JsonPlayerReader(JSON_PLAYER_LOCATION);
        league = new League(allTeams);
        playerAssociation = new PlayerAssociation(allPlayers);
        while (true) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            if (command == 0) {
                break;
            }
            commandCases(command);
        }
    }

    // EFFECTS: Runs the corresponding input menu and actions based on command
    @SuppressWarnings("methodlength")
    private void commandCases(int command) {
        switch (command) {
            case 1:
                teamInput();
                break;
            case 2:
                playerInput();
                break;
            case 3:
                listTeamStatsInput();
                break;
            case 4:
                listPlayerStatsInput();
                break;
            case 5:
                compareTeamsInput();
                break;
            case 6:
                comparePlayersInput();
                break;
            case 7:
                saveTeams(league);
                savePlayers(playerAssociation);
                break;
            case 8:
                loadTeams();
                loadPlayers();
                break;
        }
    }

    // EFFECTS: Displays the menu of options the user can use
    private void displayMenu() {
        System.out.println("Enter a number as your action");
        System.out.println("[1] Create Team");
        System.out.println("[2] Create Player");
        System.out.println("[3] List team stats");
        System.out.println("[4] List player stats");
        System.out.println("[5] Compare two teams");
        System.out.println("[6] Compare two players");
        System.out.println("[7] Save teams and players");
        System.out.println("[8] Load teams and players");
        System.out.println("If you want to exit, type \"0\"");
    }

    // EFFECTS: Creates a team with team name and game stats of each player in team
    private void teamInput() {
        System.out.println("What is your team name?");
        String teamName = input.nextLine();

        System.out.println("How many games has your team played?");
        int numGames = input.nextInt();
        input.nextLine();

        List<Player> playerList = createPlayerStatsInTeam(numGames);
        allPlayers.addAll(playerList);
        Team team = makeTeam(playerList, teamName);
        allTeams.add(team);
    }

    // REQUIRES: playerList.size() == 5
    // EFFECTS: Creates a team based on playerList and teamName
    private Team makeTeam(List<Player> playerList, String teamName) {
        Player top = playerList.get(0);
        Player jg = playerList.get(1);
        Player mid = playerList.get(2);
        Player bot = playerList.get(3);
        Player supp = playerList.get(4);
        return new Team(teamName, new ArrayList<Player>(), top, jg, mid, bot, supp);
    }

    // REQUIRES: numGames > 0
    // EFFECTS: Creates game stats for each player in team for each game
    private List<Player> createPlayerStatsInTeam(int numGames) {
        List<String> playerRoles = new ArrayList<>(Arrays.asList("Top", "Jungle", "Mid", "Bot", "Support"));
        List<Player> playersInTeam = new ArrayList<>();
        for (int numPlayer = 0; numPlayer < 5; numPlayer++) {
            ArrayList<GameStats> roleStats = new ArrayList<>();
            System.out.println("What is your " + playerRoles.get(numPlayer) + "'s username?");
            String username = input.nextLine();

            for (int gameCount = 1; gameCount <= numGames; gameCount++) {
                GameStats roleStat = gameStatQuestions(gameCount, username);
                roleStats.add(roleStat);
            }
            Player player = new Player(username, roleStats);
            playersInTeam.add(player);
        }
        return playersInTeam;
    }

    // EFFECTS: Creates player from user input of username and game counts
    private void playerInput() {
        System.out.println("What is your player's username?");
        String username = input.nextLine();

        System.out.println("How many games has your player played?");
        int numGames = input.nextInt();

        ArrayList<GameStats> stats = createPlayerStats(username, numGames);
        Player player = new Player(username, stats);
        allPlayers.add(player);
    }

    // EFFECTS: Creates player stats of numGames games
    private ArrayList<GameStats> createPlayerStats(String username, int numGames) {
        ArrayList<GameStats> stats = new ArrayList<>();
        for (int gameCount = 1; gameCount <= numGames; gameCount++) {
            GameStats stat = gameStatQuestions(gameCount, username);
            stats.add(stat);
        }
        return stats;
    }

    // EFFECTS: Asks user for two teams to compare and prints out comparisons for each team
    private void compareTeamsInput() {
        System.out.println("Which two teams do you want to compare");
        System.out.println(printAllTeamNames());
        System.out.println("Enter your first team: ");
        String nameOne = input.nextLine();
        System.out.println("Enter your second team: ");
        String nameTwo = input.nextLine();
        Team teamOne = findTeamFromName(nameOne);
        Team teamTwo = findTeamFromName(nameTwo);
        compareTeams(teamOne, teamTwo);
    }

    // EFFECTS: Prints out the comparison of each team's players and the team wide stats like total damage,
    //          total gold, or game time
    private void compareTeams(Team one, Team two) {
        for (int roleCount = 0; roleCount < 5; roleCount++) {
            Player currentPlayerFromTeamOne = one.getPlayers().get(roleCount);
            Player currentPlayerFromTeamTwo = two.getPlayers().get(roleCount);
            compareTeamPlayers(currentPlayerFromTeamOne, currentPlayerFromTeamTwo);
        }
        compareTeamPlayersDmgPercentShare(one, two);
        compareTeamPlayersGoldPercentShare(one, two);
        System.out.println(one.getTeamName() + ": " + one.getTotalTeamDamagePerMin() + " total DPM");
        System.out.println(two.getTeamName() + ": " + two.getTotalTeamDamagePerMin() + " total DPM\n");
        System.out.println(one.getTeamName() + ": " + one.getTotalTeamGoldPerMin() + " total GPM");
        System.out.println(two.getTeamName() + ": " + two.getTotalTeamGoldPerMin() + " total GPM\n");
        String teamOneGameTime = one.getPlayers().get(0).getAverageGameTime();
        String teamTwoGameTime = two.getPlayers().get(0).getAverageGameTime();
        System.out.println(one.getTeamName() + ": " + teamOneGameTime + " mins");
        System.out.println(two.getTeamName() + ": " + teamTwoGameTime + " mins\n");
    }

    // EFFECTS: Prints out the comparison of first and second's stats
    private void compareTeamPlayers(Player first, Player second) {
        System.out.println(first.getUsername() + ": " + first.getAverageKills() + " kills");
        System.out.println(second.getUsername() + ": " + second.getAverageKills() + " kills\n");
        System.out.println(first.getUsername() + ": " + first.getAverageDeaths() + " deaths");
        System.out.println(second.getUsername() + ": " + second.getAverageDeaths() + " deaths\n");
        System.out.println(first.getUsername() + ": " + first.getAverageAssists() + " assists");
        System.out.println(second.getUsername() + ": " + second.getAverageAssists() + " assists\n");
        System.out.println(first.getUsername() + ": " + first.getAverageKDA() + " KDA");
        System.out.println(second.getUsername() + ": " + second.getAverageKDA() + " KDA\n");
        System.out.println(first.getUsername() + ": " + first.getAverageCsPerMin() + " CSPM");
        System.out.println(second.getUsername() + ": " + second.getAverageCsPerMin() + " CSPM\n");
        System.out.println(first.getUsername() + ": " + first.getAverageDmgPerMin() + " DPM");
        System.out.println(second.getUsername() + ": " + second.getAverageDmgPerMin() + " DPM\n");
        System.out.println(first.getUsername() + ": " + first.getAverageGoldPerMin() + " GPM");
        System.out.println(second.getUsername() + ": " + second.getAverageGoldPerMin() + " GPM\n");
    }

    // EFFECTS: Prints out the damage share of each role in team one and team two
    private void compareTeamPlayersDmgPercentShare(Team one, Team two) {
        System.out.println("Damage Percent In Team\n");
        System.out.println(one.getTeamName() + " Top: " + one.getDmgPercentFromRole("Top") + "\n");
        System.out.println(two.getTeamName() + " Top: " + two.getDmgPercentFromRole("Top") + "\n");

        System.out.println(one.getTeamName() + " Jg: " + one.getDmgPercentFromRole("Jg"));
        System.out.println(two.getTeamName() + " Jg: " + two.getDmgPercentFromRole("Jg") + "\n");

        System.out.println(one.getTeamName() + " Mid: " + one.getDmgPercentFromRole("Mid"));
        System.out.println(two.getTeamName() + " Mid: " + two.getDmgPercentFromRole("Mid") + "\n");

        System.out.println(one.getTeamName() + " Bot: " + one.getDmgPercentFromRole("Bot"));
        System.out.println(two.getTeamName() + " Bot: " + two.getDmgPercentFromRole("Bot") + "\n");

        System.out.println(one.getTeamName() + " Supp: " + one.getDmgPercentFromRole("Supp"));
        System.out.println(two.getTeamName() + " Supp: " + two.getDmgPercentFromRole("Supp") + "\n");
    }

    // EFFECTS: Prints out the gold share of each role in team one and team two
    private void compareTeamPlayersGoldPercentShare(Team one, Team two) {
        System.out.println("Gold Percent In Team\n");
        System.out.println(one.getTeamName() + " Top: " + one.getGoldPercentFromRole("Top") + "\n");
        System.out.println(two.getTeamName() + " Top: " + two.getGoldPercentFromRole("Top") + "\n");

        System.out.println(one.getTeamName() + " Jg: " + one.getGoldPercentFromRole("Jg"));
        System.out.println(two.getTeamName() + " Jg: " + two.getGoldPercentFromRole("Jg") + "\n");

        System.out.println(one.getTeamName() + " Mid: " + one.getGoldPercentFromRole("Mid"));
        System.out.println(two.getTeamName() + " Mid: " + two.getGoldPercentFromRole("Mid") + "\n");

        System.out.println(one.getTeamName() + " Bot: " + one.getGoldPercentFromRole("Bot"));
        System.out.println(two.getTeamName() + " Bot: " + two.getGoldPercentFromRole("Bot") + "\n");

        System.out.println(one.getTeamName() + " Supp: " + one.getGoldPercentFromRole("Supp"));
        System.out.println(two.getTeamName() + " Supp: " + two.getGoldPercentFromRole("Supp") + "\n");
    }

    // EFFECTS: Asks for two players to compare and which stat they would like to compare the players from
    //          and prints out the comparison
    private void comparePlayersInput() {
        System.out.println("Which two players do you want to compare?");
        System.out.println(printAllPlayersUsernames());
        System.out.println("Enter your first player: ");
        String usernameOne = input.nextLine();
        System.out.println("Enter your second player: ");
        String usernameTwo = input.nextLine();
        Player playerOne = findPlayerFromUsername(usernameOne);
        Player playerTwo = findPlayerFromUsername(usernameTwo);
        System.out.println("What do you want to compare? Enter number for which comparison");
        System.out.println("[1] Kills");
        System.out.println("[2] Deaths");
        System.out.println("[3] Assists");
        System.out.println("[4] KDA");
        System.out.println("[5] CS per min");
        System.out.println("[6] Damage per min");
        System.out.println("[7] Gold per min");
        System.out.println("[8] Gametime");
        int commandNum = input.nextInt();
        comparePlayers(playerOne, playerTwo, commandNum);
    }

    // EFFECTS: Prints out a comparison of first's chosen stats compared to second's chosen stats
    @SuppressWarnings("methodlength")
    private void comparePlayers(Player first, Player second, int commandNum) {
        switch (commandNum) {
            case 1:
                System.out.println(first.getUsername() + ": " + first.getAverageKills() + " kills");
                System.out.println(second.getUsername() + ": " + second.getAverageKills() + " kills");
                break;
            case 2:
                System.out.println(first.getUsername() + ": " + first.getAverageDeaths() + " deaths");
                System.out.println(second.getUsername() + ": " + second.getAverageDeaths() + " deaths");
                break;
            case 3:
                System.out.println(first.getUsername() + ": " + first.getAverageAssists() + " assists");
                System.out.println(second.getUsername() + ": " + second.getAverageAssists() + " assists");
                break;
            case 4:
                System.out.println(first.getUsername() + ": " + first.getAverageKDA() + " KDA");
                System.out.println(second.getUsername() + ": " + second.getAverageKDA() + " KDA");
                break;
            case 5:
                System.out.println(first.getUsername() + ": " + first.getAverageCsPerMin() + " CSPM");
                System.out.println(second.getUsername() + ": " + second.getAverageCsPerMin() + " CSPM");
                break;
            case 6:
                System.out.println(first.getUsername() + ": " + first.getAverageDmgPerMin() + " DPM");
                System.out.println(second.getUsername() + ": " + second.getAverageDmgPerMin() + " DPM");
                break;
            case 7:
                System.out.println(first.getUsername() + ": " + first.getAverageGoldPerMin() + " GPM");
                System.out.println(second.getUsername() + ": " + second.getAverageGoldPerMin() + " GPM");
                break;
            case 8:
                System.out.println(first.getUsername() + ": " + first.getAverageGameTime() + " minutes");
                System.out.println(second.getUsername() + ": " + second.getAverageGameTime() + " minutes");
                break;
        }
    }

    // EFFECTS: Asks for team name of team that user wants stats for and prints them out
    private void listTeamStatsInput() {
        System.out.println("Enter the team that you want to list stats for");
        System.out.println(printAllTeamNames());
        String teamName = input.nextLine();
        Team team = findTeamFromName(teamName);
        listTeamStats(team);
    }

    // EFFECTS: Prints out each player's stats in team
    private void listTeamStats(Team team) {
        for (int i = 0; i < 5; i++) {
            Player currentPlayer = team.getPlayers().get(i);
            listPlayerStats(currentPlayer);
            System.out.println("");
        }
    }

    // EFFECTS: Asks for username of player that user wants stats for and prints them out
    private void listPlayerStatsInput() {
        System.out.println("Enter the player that you want to list stats for");
        System.out.println(printAllPlayersUsernames());
        String username = input.nextLine();
        Player player = findPlayerFromUsername(username);
        listPlayerStats(player);
    }

    // EFFECTS: Prints out Player's game stats
    private void listPlayerStats(Player player) {
        System.out.println(player.getUsername() + ": " + player.getAverageKills() + " kills");

        System.out.println(player.getUsername() + ": " + player.getAverageDeaths() + " deaths");

        System.out.println(player.getUsername() + ": " + player.getAverageAssists() + " assists");

        System.out.println(player.getUsername() + ": " + player.getAverageKDA() + " KDA");

        System.out.println(player.getUsername() + ": " + player.getAverageCsPerMin() + " CSPM");

        System.out.println(player.getUsername() + ": " + player.getAverageDmgPerMin() + " DPM");

        System.out.println(player.getUsername() + ": " + player.getAverageGoldPerMin() + " GPM");
    }

    // EFFECTS: Returns an ArrayList of usernames of all players created
    private ArrayList<String> printAllPlayersUsernames() {
        ArrayList<String> playerUsernames = new ArrayList<>();
        for (Player players : allPlayers) {
            String username = players.getUsername();
            playerUsernames.add(username);
        }
        return playerUsernames;
    }

    // EFFECTS: Returns an ArrayList of team names of all teams created
    private ArrayList<String> printAllTeamNames() {
        ArrayList<String> teamNames = new ArrayList<>();
        for (Team teams : allTeams) {
            String username = teams.getTeamName();
            teamNames.add(username);
        }
        return teamNames;
    }

    // EFFECTS: Returns player based on the given username corresponding to the player's username
    private Player findPlayerFromUsername(String username) {
        for (int i = 0; i < allPlayers.size(); i++) {
            String currentUsername = allPlayers.get(i).getUsername();
            if (username.equals(currentUsername)) {
                return allPlayers.get(i);
            }
        }
        return null;
    }

    // EFFECTS: Returns team based on the given name corresponding to the team's name
    private Team findTeamFromName(String name) {
        for (int i = 0; i < allTeams.size(); i++) {
            String currentName = allTeams.get(i).getTeamName();
            if (name.equals(currentName)) {
                return allTeams.get(i);
            }
        }
        return null;
    }

    // REQUIRES: gameCount > 0
    // EFFECTS: Asks users questions about player's stats in a game and creates GameStat from answers
    private GameStats gameStatQuestions(int gameCount, String username) {
        System.out.println("In game " + gameCount + ", how many kills did " + username + " end with?");
        int kills = input.nextInt();
        System.out.println("In game " + gameCount + ", how many deaths did " + username + " end with?");
        int deaths = input.nextInt();
        System.out.println("In game " + gameCount + ", how many assists did " + username + " end with?");
        int assists = input.nextInt();
        System.out.println("In game " + gameCount + ", how much cs did " + username + " end with?");
        int cs = input.nextInt();
        System.out.println("In game " + gameCount + ", how much damage did " + username + " end with?");
        int damage = input.nextInt();
        System.out.println("In game " + gameCount + ", how much gold did " + username + " end with?");
        int gold = input.nextInt();
        input.nextLine();
        System.out.println("In game " + gameCount + ", what time did " + username + " end the game at?");
        String gameTime = input.nextLine();
        return new GameStats(kills, deaths, assists, cs, damage, gold, gameTime);
    }

    // EFFECTS: Saves players to JSON_PLAYER_LOCATION
    private void savePlayers(PlayerAssociation playerAssociation) {
        try {
            jsonPlayerWriter.open();
            jsonPlayerWriter.write(playerAssociation);
            jsonPlayerWriter.close();
            System.out.println("Saved players to " + JSON_PLAYER_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_PLAYER_LOCATION);
        }
    }

    // EFFECTS: Loads players from JSON_PLAYER_LOCATION and adds them to allPlayers list
    private void loadPlayers() {
        try {
            PlayerAssociation playerAssociation = jsonPlayerReader.read();
            List<Player> players = playerAssociation.getAllPlayers();
            allPlayers.addAll(players);
            System.out.println("Loaded players from " + JSON_PLAYER_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_PLAYER_LOCATION);
        }
    }

    // EFFECTS: Saves teams to JSON_TEAM_LOCATION
    private void saveTeams(League league) {
        try {
            jsonTeamWriter.open();
            jsonTeamWriter.write(league);
            jsonTeamWriter.close();
            System.out.println("Saved teams to " + JSON_TEAM_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_TEAM_LOCATION);
        }
    }

    // EFFECTS: Loads teams from JSON_TEAM_LOCATION and adds them to allTeams list
    private void loadTeams() {
        try {
            League league = jsonTeamReader.read();
            List<Team> teams = league.getAllTeams();
            allTeams.addAll(teams);
            System.out.println("Loaded teams from " + JSON_TEAM_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_TEAM_LOCATION);
        }
    }

    // EFFECTS: Adds all players from teams to a new list of players
    private List<Player> addPlayersFromTeams(List<Team> teams) {
        List<Player> allPlayersFromTeams = new ArrayList<>();
        for (Team team : teams) {
            List<Player> teamPlayers = team.getPlayers();
            allPlayersFromTeams.addAll(teamPlayers);
        }
        return allPlayersFromTeams;
    }

    public List<Team> getAllTeams() {
        return allTeams;
    }
}
