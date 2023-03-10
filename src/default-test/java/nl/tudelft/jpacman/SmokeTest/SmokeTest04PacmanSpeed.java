package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * End-To-End Test how to ensure that pacman have constant speed
 * by checking score of eating pellets when press a key in any action
 *
 * @author Saksit Sirisakda
 */
public class SmokeTest04PacmanSpeed {
    /**
     * The game launcher for init the game
     */
    private Launcher launcher;
    /**
     * The robot for making mouse and press event
     */
    private Robot bot;
    /**
     * Period of eating pellets
     */
    private long intervalTime = 500;
    /**
     * Total score that Pacman do in <code>IntervalTime</code>
     */
    private int scoreInIntervalTime = 50;
    /**
     * Error of score in one period of time
     */
    private int scoreEpsilon = 30;

    /**
     * Open the game
     * @throws AWTException
     */
    @BeforeEach
    void setUp() throws AWTException{
        launcher = new Launcher();
        bot = new Robot();
    }

    /**
     * Close the game
     */
    @AfterEach
    void tearDown(){
        launcher.dispose();
    }

    /**
     * Player score should always equal the expected score
     * or in interval error when press key once
     * @throws InterruptedException
     */
    @Test
    void pacmanSpeedTest()
        throws InterruptedException{
        launcher.withMapFile("/testBoard/board.txt").launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        clickStartBtn();
        enterKey(KeyEvent.VK_RIGHT);
        Thread.sleep(intervalTime);

        assertEquals(scoreInIntervalTime,player.getScore(),scoreEpsilon);
    }
    /**
     * Player score should always equal the expected score
     * or in interval error when press key many times
     * @throws InterruptedException
     */
    @Test
    void pacmanSpeedTestInterval() throws InterruptedException{
        launcher.withMapFile("/testBoard/board.txt").launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        clickStartBtn();
        enterKeyInterval(KeyEvent.VK_RIGHT);

        assertEquals(scoreInIntervalTime,player.getScore(),scoreEpsilon);
    }

    /**
     * Move mouse to start btn then click
     */
    void clickStartBtn(){
        bot.mouseMove(   600,120);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Simulate holding key on keyboard in <code>intervalTime/code>
     *
     * @param key {@link KeyEvent} to simulate
     * @throws InterruptedException
     */
    void enterKeyInterval(int key) throws InterruptedException{
        long timeInterval = System.currentTimeMillis() + intervalTime;
        while(System.currentTimeMillis() < timeInterval){
            bot.keyPress(key);
        }
        bot.keyRelease(key);
    }

    /**
     * Simulate pressing key on keyboard
     *
     * @param key {@link KeyEvent} to simulate
     * @throws InterruptedException
     */
    void enterKey(int key) throws InterruptedException{
        bot.keyPress(key);
        bot.keyRelease(key);
    }
}
