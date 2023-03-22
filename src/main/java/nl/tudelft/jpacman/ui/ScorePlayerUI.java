package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;

import javax.swing.*;



public class ScorePlayerUI extends JFrame{
    private JPanel scoreBoardUI;

    public ScorePlayerUI(Game game){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        scoreBoardUI = new JPanel();

        JButton MenuButton = new JButton("Menu");
        JButton ScoreBoardButton = new JButton("Scoreboard");
        JButton RestartButton = new JButton("Restart");

        JLabel PUsername, PTime, PScore;
        String PlayerUsername, PlayerTime, PlayerScore;

        PlayerUsername = game.getPlayers().get(0).getName();
        PlayerTime = "" + game.getTime()/1000.0;
        PlayerScore = "" + game.getPlayers().get(0).getScore();

        PUsername = new JLabel("Username : " + PlayerUsername);
        PTime = new JLabel("Time(s) : " + PlayerTime);
        PScore = new JLabel("Score : " + PlayerScore);


        MenuButton.addActionListener(e -> {
            MenuUI menuUI = new MenuUI();
            menuUI.start();
            dispose();
        });

        ScoreBoardButton.addActionListener(e -> {
            ScoreBoardUI scoreBoardUI = new ScoreBoardUI(game);
            dispose();
        });

        RestartButton.addActionListener(e -> {
            Launcher.launchers.launch(PlayerUsername);
            dispose();
        });

        PUsername.setBounds(50,50, 300,20);
        PTime.setBounds(50,100, 120,20);
        PScore.setBounds(50,150, 120,20);

        MenuButton.setBounds(30,300, 80,20);
        ScoreBoardButton.setBounds(130,300, 120,20);
        RestartButton.setBounds(260,300, 80,20);

        add(PUsername);
        add(PTime);
        add(PScore);

        add(MenuButton);
        add(ScoreBoardButton);
        add(RestartButton);

        setSize(400,400);
        add(scoreBoardUI);

        setVisible(true);
    }
}
