package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-To-End Test how to control pacman direction
 * by using arrow btn and A, S, D, and W on keyboard
 *
 * @author Anirut Chaogla
 */
public class SmokeTest03Movement {
    private Launcher launcher;
    private Robot bot;

    /**
     * Open the game
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
     * Pacman should automatically move forward and
     * can use key A, S, D, W and arrow keys
     * to change Pacman direction
     *
     * @param source Path to map file (.txt)
     * @param key {@link KeyEvent} to control Pacman
     * @param expectedScore Expected player score
     * @param name Name of test case
     * @throws InterruptedException
     */
    @ParameterizedTest(name = "TC{index} {3} ")
    @CsvSource({
        "/testBoard/boardMoveUp.txt," + KeyEvent.VK_UP + ", 60" + ",Arrow Up pacman should go Up",
        "/testBoard/boardMoveUp.txt," + KeyEvent.VK_W + ", 60" + ",Arrow W pacman should go Up",
        "/testBoard/boardMoveDown.txt," + KeyEvent.VK_DOWN + ", 60" + ",Arrow Down pacman should go Down",
        "/testBoard/boardMoveDown.txt," + KeyEvent.VK_S + ", 60" + ",Arrow S pacman should go Down",
        "/testBoard/boardMoveLeft.txt," + KeyEvent.VK_LEFT + ", 100" + ",Arrow Left pacman should go Left",
        "/testBoard/boardMoveLeft.txt," + KeyEvent.VK_A + ", 100" + ",Arrow A pacman should go Left",
        "/testBoard/boardMoveRight.txt," + KeyEvent.VK_RIGHT + ", 100" + ",Arrow Right pacman should go Right",
        "/testBoard/boardMoveRight.txt," + KeyEvent.VK_D + ", 100" + ",Arrow D pacman should go Right"
    })
    void turnAround(String source, int key, int expectedScore, String name)
                                    throws InterruptedException{
        launcher.withMapFile(source).launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        long delayAfterPressKey = 900L;

        clickStartBtn();
        enterKey(key,delayAfterPressKey);

        assertThat(player.getScore()).isEqualTo(expectedScore);
    }

    /**
     * Move mouse to start btn then click
     */
    void clickStartBtn(){
        bot.mouseMove(150,320);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Simulate pressing key on keyboard
     *
     * @param key {@link KeyEvent} to simulate
     * @param delay delay after press key
     * @throws InterruptedException
     */
    void enterKey(int key, long delay) throws InterruptedException{
        bot.keyPress(key);
        bot.keyRelease(key);
        Thread.sleep(delay);
    }
}
