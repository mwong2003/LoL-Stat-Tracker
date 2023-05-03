package ui.panels;

import model.Event;
import model.EventLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

// Represents a panel that serves as a loading screen visual component
public class LoadUpPanel implements ActionListener {

    private JFrame frame;
    private JPanel loadUpPanel;
    private JButton mainMenu;
    private BufferedImage image;

    // EFFECTS: Creates a load up panel that has the LoL logo and a continue button
    public LoadUpPanel() {
        frame = new JFrame();
        loadUpPanel = new JPanel();
        //loadUpPanel.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));
        setupFrame();
        try {
            image = ImageIO.read(new File("./data/lollogo.png"));
        } catch (Exception e) {
            System.out.println("Could not find image path");
        }
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        loadUpPanel.add(imageLabel);
        loadUpPanel.setBackground(Color.black);
        mainMenu = new JButton("Continue");
        mainMenu.addActionListener(this);
        loadUpPanel.add(mainMenu);
        frame.add(loadUpPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Setups frame with proper settings
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
    }

    // MODIFIES: this
    // EFFECTS: If user presses continue button, takes them to main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Continue")) {
            frame.dispose();
            MainPanel mp = new MainPanel();
        }
    }
}
