package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;

import javax.swing.*;
import java.awt.*;

public class UsernameUI extends JFrame{
    private JPanel usernameUI;
    private String username ;
    public UsernameUI() {

        usernameUI = new JPanel();

        // User name Label
        JLabel usernameLabel = new JLabel("Username", JLabel.CENTER);
        usernameLabel.setBounds(100,50, 200,100);

        // Text Label
        JTextField textLabel = new JTextField();
        textLabel.setBounds(100,125, 200,50);

        // Button Label
        JButton enterButton = new JButton("Enter");
        enterButton.setBounds(150,200, 100,50);

        // Action after click "Enter"
        enterButton.addActionListener(e -> {

            // String Text "Username"
            username = textLabel.getText();

            // Check Text "empty"
            if (!username.isEmpty()){
                Launcher.launchers.launch(username);
                dispose();
            }
        });

        usernameUI.setLayout(new GridLayout(4,1));
        usernameUI.add(usernameLabel);
        usernameUI.add(textLabel);
        usernameUI.add(enterButton);
        setSize(400,400);
        add(usernameUI);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void start() {
        setVisible(true);
    }
}
