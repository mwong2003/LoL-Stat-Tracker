package ui.panels;

import model.Event;
import model.EventLog;
import model.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

// Represents a skeleton of a team before adding players to the team
public class CreateTeamPanel implements ActionListener {
    private List<Team> allTeams;
    private JFrame frame;
    private JPanel createTeamPanel;
    private JTextField teamNameInput;
    private JTextField topNameInput;
    private JTextField jgNameInput;
    private JTextField midNameInput;
    private JTextField botNameInput;
    private JTextField suppNameInput;
    private JTextField numGamesPlayed;
    private JButton nextButton;
    private JFrame mainFrame;
    private JPanel mainPanel;

    // EFFECTS: Generates a team skeleton with team name, player names, and number of games played
    public CreateTeamPanel(JFrame mainFrame, JPanel mainPanel, List<Team> allTeams) {
        this.allTeams = allTeams;
        this.mainPanel = mainPanel;
        this.mainFrame = mainFrame;
        frame = new JFrame();
        nextButton.addActionListener(this);
        //createTeamPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
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
        frame.setContentPane(createTeamPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: If next button is pressed, takes user to create players on the team panel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            frame.dispose();
            String teamName = teamNameInput.getText();
            String strNumGames = numGamesPlayed.getText();
            int numGames = Integer.parseInt(strNumGames);
            String topName = topNameInput.getText();
            String jgName = jgNameInput.getText();
            String midName = midNameInput.getText();
            String botName = botNameInput.getText();
            String suppName = suppNameInput.getText();
            CreatePlayersOnTeamPanel createPlayersOnTeamPanel = new CreatePlayersOnTeamPanel(teamName, numGames,
                    topName, jgName, midName, botName, suppName, mainFrame, mainPanel, allTeams);
            JPanel panel = createPlayersOnTeamPanel.getCreatePlayersOnTeamPanel();
            panel.setVisible(true);
        }
    }

    public JPanel getCreateTeamPanel() {
        return createTeamPanel;
    }
}
