package ui.panels;

import model.Event;
import model.EventLog;
import model.League;
import model.Team;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

// Represents that task to remove a specific team the user wants to remove
public class RemoveTeamPanel implements ActionListener {
    private JPanel removeTeamPanel;
    private JTextField removeTeamInput;
    private JButton removeTeamButton;
    private JLabel updateMessage;
    private JButton mainMenuButton;
    private JFrame frame;
    private JFrame mainFrame;
    private League league;

    // EFFECTS: Generates a panel for user to remove a team from allTeams
    public RemoveTeamPanel(JFrame mainFrame, League league) {
        this.mainFrame = mainFrame;
        this.league = league;
        frame = new JFrame();
        removeTeamButton.addActionListener(this);
        mainMenuButton.addActionListener(this);
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
        frame.setContentPane(removeTeamPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: If remove team button is pressed, removes team if user entered a team name that was in allTeams
    //          If main menu button is pressed, takes user back to main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Remove team")) {
            String teamName = removeTeamInput.getText();
            removeTeam(teamName);
        } else if (e.getActionCommand().equals("Back to main menu")) {
            frame.dispose();
            mainFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes team with teamName in allTeams if it exists
    private void removeTeam(String teamName) {
        List<Team> allTeams = league.getAllTeams();
        int currentSize = allTeams.size();
        league.removeTeam(teamName);
        if (allTeams.size() == currentSize) {
            updateMessage.setText(teamName + " was not found");
        } else {
            updateMessage.setText(teamName + " has been removed");
        }
    }
}
