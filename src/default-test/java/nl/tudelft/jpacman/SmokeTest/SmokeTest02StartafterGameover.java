package nl.tudelft.jpacman.SmokeTest;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.awt.*;
import java.awt.event.InputEvent;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


/**
 *Test Start after Game over


 * @author  Netchanok Muaengkhot
 */
public class SmokeTest02StartafterGameover {
    /**
     * The game launcher for init the game
     */
    private Launcher launcher;
    /**
     * The robot for making mouse and press event
     */
    private Robot bot;




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
    @Test
    public void gameIsOpenning() {
        launcher.launch();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    @Test
    void startAfterGameover()
        throws InterruptedException{
        launcher.withMapFile("/testBoard/board2.txt").launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);



        //simulate playing game
        // start
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        // now moving back does not change the score
        game.move(player, Direction.WEST);
        assertThat(player.getScore()).isEqualTo(10);

        // try to move as far as we can
        move(game, Direction.EAST, 7);
        assertThat(player.getScore()).isEqualTo(60);

        // move towards the monsters
        move(game, Direction.NORTH, 6);
        assertThat(player.getScore()).isEqualTo(120);

        // no more points to earn here.
        move(game, Direction.WEST, 2);
        assertThat(player.getScore()).isEqualTo(120);

        move(game, Direction.NORTH, 2);
        Thread.sleep(500L);
        // close to monsters, this will get us killed.
        move(game, Direction.WEST, 10);
        move(game, Direction.EAST, 10);
        assertThat(player.isAlive()).isFalse();
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        //Restart Game
        clickRestartBtn();
        Thread.sleep(1000L);
        clickStartBtn();
        Thread.sleep(500L);
        assertThat(getGame().isInProgress()).isTrue();

    }


    /**
     * Move mouse to start btn then click
     */
    void clickStartBtn(){
        bot.mouseMove(   135,410);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    void clickRestartBtn(){
        bot.mouseMove(   230,410);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }



    private Game getGame() {
        return launcher.getGame();
    }

    public static void move(Game game, Direction dir, int numSteps) {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }
}

