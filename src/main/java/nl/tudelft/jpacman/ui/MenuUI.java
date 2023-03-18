package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;

import javax.swing.*;
import java.awt.*;

public class MenuUI extends JFrame {
    private JPanel menuUI;

    public MenuUI() {
        menuUI = new JPanel();
        JLabel menuLabel = new JLabel("Menu", JLabel.CENTER);
        JButton onlineButton = new JButton("Online");
        JButton offlineButton = new JButton("Offline");
        offlineButton.addActionListener(e -> {
            UsernameUI usernameUI = new UsernameUI();
            usernameUI.start();
            dispose();
        });
        JButton exitButton = new JButton("exit");
        menuUI.setLayout(new GridLayout(4,1));
        menuUI.add(menuLabel);
        menuUI.add(onlineButton);
        menuUI.add(offlineButton);
        menuUI.add(exitButton);
        setSize(400,400);
        add(menuUI);
    }

    public void start() {
        setVisible(true);
    }
}
