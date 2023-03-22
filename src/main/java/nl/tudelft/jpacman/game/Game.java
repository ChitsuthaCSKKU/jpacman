package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.data.ScoreRepository;
import nl.tudelft.jpacman.data.Score;
import nl.tudelft.jpacman.data.ScoreData;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.ui.ScorePlayerUI;


import java.util.List;

/**
 * A basic implementation of a Pac-Man game.
 *
 * @author Jeroen Roosen
 */
public abstract class Game implements LevelObserver {

    private long timestampStart;
    private long time = 0;
    /**
     * Object that locks the start and stop methods.
     */
    private final Object progressLock = new Object();
    /**
     * <code>true</code> if the game is in progress.
     */
    private boolean inProgress;
    /**
     * The algorithm used to calculate the points that
     * they player gets whenever some action happens.
     */
    private PointCalculator pointCalculator;

    /**
     * Creates a new game.
     *
     * @param pointCalculator The way to calculate points upon collisions.
     */
    protected Game(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
        inProgress = false;
        timestampStart = System.currentTimeMillis();
        time = 0;
    }

    /**
     * Starts or resumes the game.
     */
    public void start() {
        synchronized (progressLock) {
            if (isInProgress()) {
                System.out.println("Game is already started.");
                return;
            }
            if (getLevel().isAnyPlayerAlive() && getLevel().remainingPellets() > 0) {
                timestampStart = System.currentTimeMillis()-time;
                inProgress = true;
                getLevel().addObserver(this);
                getLevel().start();
            }
        }
    }

    /**
     * Pauses the game.
     */
    public void stop() {
        synchronized (progressLock) {
            if (!isInProgress()) {
                return;
            }
            inProgress = false;
            getLevel().stop();
        }
    }

    /**
     * @return <code>true</code> iff the game is started and in progress.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * @return An immutable list of the participants of this game.
     */
    public abstract List<Player> getPlayers();

    /**
     * @return The level currently being played.
     */
    public abstract Level getLevel();

    /**
     * Moves the specified player one square in the given direction.
     *
     * @param player    The player to move.
     * @param direction The direction to move in.
     */
    public void move(Player player, Direction direction) {
        if (isInProgress()) {
            // execute player move.
            getLevel().move(player, direction);
            pointCalculator.pacmanMoved(player, direction);
        }
    }

    public void updateTime() {
        long timestampCurrent = System.currentTimeMillis();
        time = timestampCurrent - timestampStart;
    }

    public long getTime() {
        return time;
    }

    public boolean saveScore() {
        Score score = new Score(getPlayers().get(0), getTime());
        if(!Launcher.isOnline) {
            return ScoreData.saveScore(score);
        } else {
            try {
                ScoreRepository.write(score);
                return true;
            } catch (Exception e){
                System.out.println("Can't save score to database.");
                return false;
            }
        }

    }
    @Override
    public void levelWon() {
        saveScore();
        stop();

        // Close UI
        Launcher.pacManUI.start(false);
        ScorePlayerUI scorePlayerUI = new ScorePlayerUI(this);
    }

    @Override
    public void levelLost() {
        System.out.println("Lost");
        saveScore();
        stop();

        // Close UI
        Launcher.pacManUI.start(false);
        ScorePlayerUI scorePlayerUI = new ScorePlayerUI(this);
    }
}
