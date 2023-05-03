package ui.panels;

import model.Event;
import model.EventLog;
import model.Player;
import model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Represents a panel that displays all teams created
public class DisplayTeamsPanel implements ActionListener {
    private JPanel displayPanel;
    private JFrame frame;
    private List<Team> allTeams;
    private JFrame mainFrame;

    // EFFECTS: Generates a panel that makes a table for each team in allTeams
    public DisplayTeamsPanel(List<Team> allTeams, JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.allTeams = allTeams;
        frame = new JFrame();
        displayPanel = new JPanel(new GridBagLayout());

        setupFrame();
        makeTeamTables();
        frame.add(new JScrollPane(displayPanel));
    }



    // MODIFIES: this
    // EFFECTS: Generates a table for each team in allTeams, going down vertically for each team
    private void makeTeamTables() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5, 0, 5, 0);
        c.gridy = 0;
        c.gridx = 0;

        String[] header = {"Username", "Kills", "Deaths", "Assists", "CS", "Damage", "Gold", "Game time"};
        for (int i = 0; i < allTeams.size(); i++) {
            int numGames = allTeams.get(i).getTop().getStatList().size();
            DefaultTableModel model = new DefaultTableModel(numGames * 5, 8);
            model.setColumnIdentifiers(header);
            JTable table = new JTable();
            table.setModel(model);
            displayPanel.add(new JLabel(allTeams.get(i).getTeamName()), c);
            c.gridy++;
            displayPanel.add(table.getTableHeader(), c);
            c.gridy++;
            enterDataOnTable(table, allTeams.get(i), numGames);
            displayPanel.add(table, c);
            c.gridy++;
        }
        JButton mainMenu = new JButton("Return to Main Menu");
        displayPanel.add(mainMenu, c);
        mainMenu.addActionListener(this);
    }

    // EFFECTS: When button is pressed, returns user to main menu
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return to Main Menu")) {
            frame.dispose();
            mainFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters all team data on the corresponding team table
    private void enterDataOnTable(JTable table, Team team, int numGames) {
        enterUsernames(table, team, numGames);
        enterKills(table, team, numGames);
        enterDeaths(table, team, numGames);
        enterAssists(table, team, numGames);
        enterCS(table, team, numGames);
        enterDmg(table, team, numGames);
        enterGold(table, team, numGames);
        enterTime(table, team, numGames);
    }

    // MODIFIES: this
    // EFFECTS: Enters game time data on the corresponding team table
    private void enterTime(JTable table, Team team, int numGames) {
        List<String> gameTimes = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                String time = currentPlayer.getStatList().get(j).getGameTime();
                gameTimes.add(time);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            String time = gameTimes.get(i);
            table.setValueAt(time, i, 7);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters gold data on the corresponding team table
    private void enterGold(JTable table, Team team, int numGames) {
        List<Integer> golds = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int gold = currentPlayer.getStatList().get(j).getGold();
                golds.add(gold);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int gold = golds.get(i);
            table.setValueAt(gold, i, 6);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters damage data on the corresponding team table
    private void enterDmg(JTable table, Team team, int numGames) {
        List<Integer> damage = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int dmg = currentPlayer.getStatList().get(j).getDmg();
                damage.add(dmg);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int dmg = damage.get(i);
            table.setValueAt(dmg, i, 5);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters cs data on the corresponding team table
    private void enterCS(JTable table, Team team, int numGames) {
        List<Integer> creepScore = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int cs = currentPlayer.getStatList().get(j).getCs();
                creepScore.add(cs);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int cs = creepScore.get(i);
            table.setValueAt(cs, i, 4);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters assists data on the corresponding team table
    private void enterAssists(JTable table, Team team, int numGames) {
        List<Integer> assists = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int assist = currentPlayer.getStatList().get(j).getAssists();
                assists.add(assist);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int assist = assists.get(i);
            table.setValueAt(assist, i, 3);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters deaths data on the corresponding team table
    private void enterDeaths(JTable table, Team team, int numGames) {
        List<Integer> deaths = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int death = currentPlayer.getStatList().get(j).getDeaths();
                deaths.add(death);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int death = deaths.get(i);
            table.setValueAt(death, i, 2);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters kills data on the corresponding team table
    private void enterKills(JTable table, Team team, int numGames) {
        List<Integer> kills = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player currentPlayer = team.getPlayers().get(i);
            for (int j = 0; j < currentPlayer.getStatList().size(); j++) {
                int kill = currentPlayer.getStatList().get(j).getKills();
                kills.add(kill);
            }
        }
        for (int i = 0; i < numGames * 5; i++) {
            int kill = kills.get(i);
            table.setValueAt(kill, i, 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: Enters usernames data on the corresponding team table
    private void enterUsernames(JTable table, Team team, int numGames) {
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < team.getPlayers().size(); i++) {
            String username = team.getPlayers().get(i).getUsername();
            usernames.add(username);
        }
        int role = 0;
        int count = 1;
        for (int i = 0; i < numGames * 5; i++) {
            String username = usernames.get(role);
            table.setValueAt(username, i, 0);
            if (count == numGames) {
                role++;
                count = 1;
            } else {
                count++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets up the frame with proper settings
    private void setupFrame() {
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
        frame.pack();
        frame.setVisible(true);
    }


    public JPanel getDisplayPanel() {
        return displayPanel;
    }




}
