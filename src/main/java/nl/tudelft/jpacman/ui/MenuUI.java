package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.data.ScoreRepository;

import javax.swing.*;
import java.awt.*;

public class MenuUI extends JFrame {
    private JPanel menuPanel;

    public MenuUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuPanel = new JPanel();
        JLabel menuLabel = new JLabel("Menu", JLabel.CENTER);
        JButton onlineButton = new JButton("Online");
        onlineButton.setEnabled(ScoreRepository.canConnect());
        onlineButton.addActionListener(e -> {
            Launcher.isOnline = true;
            UsernameUI usernameUI = new UsernameUI();
            usernameUI.start();
            dispose();
        });
        JButton offlineButton = new JButton("Offline");
        offlineButton.addActionListener(e -> {
            Launcher.isOnline = false;
            UsernameUI usernameUI = new UsernameUI();
            usernameUI.start();
            dispose();
        });
        JButton exitButton = new JButton("exit");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        menuPanel.setLayout(new GridLayout(4,1));
        menuPanel.add(menuLabel);
        menuPanel.add(onlineButton);
        menuPanel.add(offlineButton);
        menuPanel.add(exitButton);
        setSize(400,400);
        add(menuPanel);
    }

    public void start() {
        setVisible(true);
    }
}
