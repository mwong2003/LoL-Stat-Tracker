package ui.panels;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents panel that allows user to create players on the team
public class CreatePlayersOnTeamPanel implements ActionListener {
    private List<Team> allTeams;
    private JPanel mainPanel;
    private JFrame frame;
    private JPanel createPlayersOnTeamPanel;
    private JLabel teamNameLabel;
    private JTextField killsInput;
    private JTextField deathsInput;
    private JTextField assistsInput;
    private JTextField csInput;
    private JTextField dmgInput;
    private JTextField goldInput;
    private JTextField timeInput;
    private JButton saveButton;
    private JTable playersOnTeamTable;
    private JLabel errorLabel;
    private JButton doneButton;
    private JFrame mainFrame;
    private String teamName;
    private String top;
    private String jg;
    private String mid;
    private String bot;
    private String supp;
    private List<String> playerNames;
    private int numGames;
    private int rowCount = 0;
    private int saveCount = 0;

    // EFFECTS: Creates panel that allows user to add players to team
    public CreatePlayersOnTeamPanel(String teamName, int numGames, String top, String jg, String mid, String bot,
                                    String supp, JFrame mainFrame, JPanel mainPanel, List<Team> allTeams) {
        this.allTeams = allTeams;
        this.mainPanel = mainPanel;
        this.mainFrame = mainFrame;
        this.numGames = numGames;
        this.teamName = teamName;
        this.top = top;
        this.jg = jg;
        this.mid = mid;
        this.bot = bot;
        this.supp = supp;
        playerNames = new ArrayList<>(Arrays.asList(top, jg, mid, bot, supp));
        teamNameLabel.setText(teamName);
        frame = new JFrame();
        //createPlayersOnTeamPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
        setupPanel(frame);
        createTable();
        enterUsernamesOnTable();
        saveButton.addActionListener(this);
        doneButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: Sets up panel and frame with proper settings
    private void setupPanel(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println(next.getDescription());
                }
                System.exit(0);
            }
        });
        frame.setContentPane(createPlayersOnTeamPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: If save button is pressed, adds stats to row on table
    //          If main menu button is pressed, goes back to main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            saveCount++;
            if (rowCount >= numGames * 5) {
                errorLabel.setText("Cannot add anymore stats to team");
            } else {
                enterPlayerStatsOnRow();
                rowCount++;
            }
        }
        if (e.getActionCommand().equals("Go back to main menu (click only if all cells in table have been filled)")) {
            Team team = createTeam();
            allTeams.add(team);
            frame.dispose();
            mainFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates table with proper amount of rows and columns to enter player stats for team
    private void createTable() {
        DefaultTableModel tblModel = new DefaultTableModel(5 * numGames, 8);
        String[] topLabels = {"Username", "Kills", "Deaths", "Assists", "CS", "Damage", "Gold", "Game time"};
        tblModel.setColumnIdentifiers(topLabels);
        playersOnTeamTable.setModel(tblModel);
    }

    // MODIFIES: this
    // EFFECTS: Enters player stats on current row
    private void enterPlayerStatsOnRow() {
        int kills = Integer.parseInt(killsInput.getText());
        int deaths = Integer.parseInt(deathsInput.getText());
        int assists = Integer.parseInt(assistsInput.getText());
        int cs = Integer.parseInt(csInput.getText());
        int dmg = Integer.parseInt(dmgInput.getText());
        int gold = Integer.parseInt(goldInput.getText());
        String gametime = timeInput.getText();

        playersOnTeamTable.setValueAt(kills, rowCount, 1);
        playersOnTeamTable.setValueAt(deaths, rowCount, 2);
        playersOnTeamTable.setValueAt(assists, rowCount, 3);
        playersOnTeamTable.setValueAt(cs, rowCount, 4);
        playersOnTeamTable.setValueAt(dmg, rowCount, 5);
        playersOnTeamTable.setValueAt(gold, rowCount, 6);
        playersOnTeamTable.setValueAt(gametime, rowCount, 7);
    }

    // MODIFIES: this
    // EFFECTS: Automatically enters usernames on table as they are already known
    private void enterUsernamesOnTable() {
        List<String> playerNames = getPlayerNames();
        int role = 0;
        int count = 1;
        for (int i = 0; i < numGames * 5; i++) {
            String currentPlayer = playerNames.get(role);
            playersOnTeamTable.setValueAt(currentPlayer, i, 0);
            if (count == numGames) {
                role++;
                count = 1;
            } else {
                count++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates team after all stats have been added on to table
    private Team createTeam() {
        if (saveCount >= numGames * 5) {
            Player top = new Player(this.top, getTopStatsFromTable());
            Player jg = new Player(this.jg, getJgStatsFromTable());
            Player mid = new Player(this.mid, getMidStatsFromTable());
            Player bot = new Player(this.bot, getBotStatsFromTable());
            Player supp = new Player(this.supp, getSuppStatsFromTable());
            Team team = new Team(teamName, new ArrayList<Player>(), top, jg, mid, bot, supp);
            return team;
        }
        return null;
    }

    // EFFECTS: Returns all the top laner's stats from table
    private List<GameStats> getTopStatsFromTable() {
        List<GameStats> topStats = new ArrayList<>();
        for (int topRow = 0; topRow < numGames; topRow++) {
            int kills = (int) playersOnTeamTable.getValueAt(topRow, 1);
            int deaths = (int) playersOnTeamTable.getValueAt(topRow, 2);
            int assists = (int) playersOnTeamTable.getValueAt(topRow, 3);
            int cs = (int) playersOnTeamTable.getValueAt(topRow, 4);
            int dmg = (int) playersOnTeamTable.getValueAt(topRow, 5);
            int gold = (int) playersOnTeamTable.getValueAt(topRow, 6);
            String gameTime = (String) playersOnTeamTable.getValueAt(topRow, 7);
            GameStats topStat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            topStats.add(topStat);
        }
        return topStats;
    }

    // EFFECTS: Returns all the jungle's stats from table
    private List<GameStats> getJgStatsFromTable() {
        List<GameStats> jgStats = new ArrayList<>();
        for (int jgRow = numGames; jgRow < numGames + numGames; jgRow++) {
            int kills = (int) playersOnTeamTable.getValueAt(jgRow, 1);
            int deaths = (int) playersOnTeamTable.getValueAt(jgRow, 2);
            int assists = (int) playersOnTeamTable.getValueAt(jgRow, 3);
            int cs = (int) playersOnTeamTable.getValueAt(jgRow, 4);
            int dmg = (int) playersOnTeamTable.getValueAt(jgRow, 5);
            int gold = (int) playersOnTeamTable.getValueAt(jgRow, 6);
            String gameTime = (String) playersOnTeamTable.getValueAt(jgRow, 7);
            GameStats jgStat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            jgStats.add(jgStat);
        }
        return jgStats;
    }

    // EFFECTS: Returns all the mid laner's stats from table
    private List<GameStats> getMidStatsFromTable() {
        List<GameStats> midStats = new ArrayList<>();
        for (int midRow = numGames * 2; midRow < (numGames * 2) + numGames; midRow++) {
            int kills = (int) playersOnTeamTable.getValueAt(midRow, 1);
            int deaths = (int) playersOnTeamTable.getValueAt(midRow, 2);
            int assists = (int) playersOnTeamTable.getValueAt(midRow, 3);
            int cs = (int) playersOnTeamTable.getValueAt(midRow, 4);
            int dmg = (int) playersOnTeamTable.getValueAt(midRow, 5);
            int gold = (int) playersOnTeamTable.getValueAt(midRow, 6);
            String gameTime = (String) playersOnTeamTable.getValueAt(midRow, 7);
            GameStats midStat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            midStats.add(midStat);
        }
        return midStats;
    }

    // EFFECTS: Returns all the bot laner's stats from table
    private List<GameStats> getBotStatsFromTable() {
        List<GameStats> botStats = new ArrayList<>();
        for (int botRow = numGames * 3; botRow < (numGames * 3) + numGames; botRow++) {
            int kills = (int) playersOnTeamTable.getValueAt(botRow, 1);
            int deaths = (int) playersOnTeamTable.getValueAt(botRow, 2);
            int assists = (int) playersOnTeamTable.getValueAt(botRow, 3);
            int cs = (int) playersOnTeamTable.getValueAt(botRow, 4);
            int dmg = (int) playersOnTeamTable.getValueAt(botRow, 5);
            int gold = (int) playersOnTeamTable.getValueAt(botRow, 6);
            String gameTime = (String) playersOnTeamTable.getValueAt(botRow, 7);
            GameStats botStat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            botStats.add(botStat);
        }
        return botStats;
    }

    // EFFECTS: Returns all the support's stats from table
    private List<GameStats> getSuppStatsFromTable() {
        List<GameStats> suppStats = new ArrayList<>();
        for (int suppRow = numGames * 2; suppRow < (numGames * 2) + numGames; suppRow++) {
            int kills = (int) playersOnTeamTable.getValueAt(suppRow, 1);
            int deaths = (int) playersOnTeamTable.getValueAt(suppRow, 2);
            int assists = (int) playersOnTeamTable.getValueAt(suppRow, 3);
            int cs = (int) playersOnTeamTable.getValueAt(suppRow, 4);
            int dmg = (int) playersOnTeamTable.getValueAt(suppRow, 5);
            int gold = (int) playersOnTeamTable.getValueAt(suppRow, 6);
            String gameTime = (String) playersOnTeamTable.getValueAt(suppRow, 7);
            GameStats suppStat = new GameStats(kills, deaths, assists, cs, dmg, gold, gameTime);
            suppStats.add(suppStat);
        }
        return suppStats;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }


    public JPanel getCreatePlayersOnTeamPanel() {
        return createPlayersOnTeamPanel;
    }
}
