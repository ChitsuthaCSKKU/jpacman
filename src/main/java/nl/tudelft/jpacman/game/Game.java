package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public boolean saveScore(Player player, long playingTime) {
        File fileScore = new File("./data/score.txt");
        System.out.println(fileScore.getPath());
        if (!(fileScore.isFile() && fileScore.exists())) {
            try {
                fileScore.createNewFile();
            } catch (IOException error){
                System.out.println("File can't create: " + error);
                return false;
            }
        }
        if(!fileScore.canWrite()){
            System.out.println("File can't write.");
            return false;
        }
        try {
            FileWriter fw = new FileWriter(fileScore, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            writer.println(player.getName() + " " + player.getScore() + " " + playingTime);
            writer.close();
        } catch (IOException error){
            System.out.println("File can't write: " + error);
            return false;
        }
        return true;
    }

    @Override
    public void levelWon() {
        saveScore(getPlayers().get(0), getTime());
        stop();
    }

    @Override
    public void levelLost() {
        saveScore(getPlayers().get(0), getTime());
        stop();
    }
}
