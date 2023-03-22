package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

/**
 * An example test class that conducts integration tests.
 */
public class StartupSystemTest {

    private Launcher launcher;

    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher();
    }

    /**
     * Close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * The simplest test that just starts the
     * game and checks it is indeed in progress.
     */
    @Test
    public void gameIsRunning() {
        launcher.launch();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    @DisplayName("New Game After Lost")
    @Test
    public void NewGameAfterLost() {
        launcher.launch();

        getGame().start();

        getGame().getPlayers().get(0).setAlive(false);

        if(launcher.getGame().getLevel().isAnyPlayerAlive() == false && launcher.getGame().getLevel().remainingPellets() > 0){
            assertThat(getGame().isInProgress()).isTrue();
        }else{
            fail("Fail test! (Lost)");
        }
    }

    @DisplayName("New Game After Win")
    @Test
    public void NewGameAfterWin() {
        launcher.launch();

        getGame().start();

        getGame().getPlayers().get(0).setAlive(true);


        if(launcher.getGame().getLevel().isAnyPlayerAlive() && launcher.getGame().getLevel().CheckZeroPellets() == 0){
            assertThat(getGame().isInProgress()).isTrue();
        }else{
            fail("Fail test! (Won)");
        }

    }

    private Game getGame() {
        return launcher.getGame();
    }
}
