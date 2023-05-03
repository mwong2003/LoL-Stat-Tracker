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
import java.util.ArrayList;
import java.util.List;

// Represents the main menu to create/remove/save/load/display teams
public class MainPanel implements ActionListener {
    private JPanel mainPanel;
    private JButton createTeamButton;
    private JButton displayTeamsButton;
    private JButton saveTeamsPlayersButton;
    private JButton loadTeamsPlayersButton;
    private JLabel actionLabel;
    private JButton removeTeamButton;
    private JFrame frame;
    private List<Team> allTeams;
    private League league;

    // EFFECTS: Generates a panel that allows user to create/remove/save/load/display teams
    public MainPanel() {
        allTeams = new ArrayList<>();
        frame = new JFrame();
        //mainPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
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
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        createTeamButton.addActionListener(this);
        displayTeamsButton.addActionListener(this);
        saveTeamsPlayersButton.addActionListener(this);
        loadTeamsPlayersButton.addActionListener(this);
        removeTeamButton.addActionListener(this);
        league = new League(allTeams);
    }

    // MODIFIES: this
    // EFFECTS: If create team button is pressed, takes user to create team panel
    //          If display team button is pressed, takes user to display teams panel
    //          If save team button is pressed, saves allTeams to JSON file
    //          If load team button is pressed, loads teams from JSON file to allTeams
    //          If remove team button is pressed, takes user to remove teams panel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create Team")) {
            createTeamAction();
        } else if (e.getActionCommand().equals("Display Teams")) {
            displayTeamsAction();
        } else if (e.getActionCommand().equals("Save Teams")) {
            saveTeams(league);
        } else if (e.getActionCommand().equals("Load Teams")) {
            loadTeams();
        } else if (e.getActionCommand().equals("Remove Team")) {
            removeTeam();
        }
    }

    // MODIFIES this
    // EFFECTS: Takes user to remove team panel
    private void removeTeam() {
        frame.setVisible(false);
        RemoveTeamPanel removeTeam = new RemoveTeamPanel(frame, new League(allTeams));
    }

    // EFFECTS: Saves teams to JSON file
    private void saveTeams(League league) {
        SaveTeam saveTeam = new SaveTeam();
        boolean saved = saveTeam.saveTeams(league);
        if (saved) {
            actionLabel.setText("Saved teams");
        } else {
            actionLabel.setText("Unable to save teams");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads teams from JSON file to allTeams
    private void loadTeams() {
        LoadTeam loadTeam = new LoadTeam(new ArrayList<>());
        this.allTeams = loadTeam.getAllTeams();
        boolean loaded = loadTeam.loadTeams();
        if (loaded) {
            actionLabel.setText("Loaded " + allTeams.size() + " teams");
        } else {
            actionLabel.setText("Unable to load teams");
        }
    }

    // MODIFIES: this
    // EFFECTS: Takes user to create team panel
    private void createTeamAction() {
        frame.setVisible(false);
        CreateTeamPanel ctp = new CreateTeamPanel(frame, mainPanel, allTeams);
    }

    // MODIFIES: this
    // EFFECTS: Takes user to display team panel
    private void displayTeamsAction() {
        frame.setVisible(false);
        DisplayTeamsPanel dtp = new DisplayTeamsPanel(allTeams, frame);
    }

}
