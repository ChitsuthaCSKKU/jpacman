package nl.tudelft.jpacman.data;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreSorterTest {

    private ScoreSorter sorter = new ScoreSorter();

    @Test
    public void testSortScoresByScoreGoodCase() {
        // Create a list of scores with different scores and times
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("Alice", 1000, 10000L));
        scores.add(new Score("Bob", 500, 5000L));
        scores.add(new Score("Charlie", 750, 7500L));

        // Sort the scores and check if they are sorted by score descending and time ascending
        List<Score> sortedScores = sorter.sortScoresByScore(scores);
        assertEquals("Alice", sortedScores.get(0).getPlayerName());
        assertEquals("Charlie", sortedScores.get(1).getPlayerName());
        assertEquals("Bob", sortedScores.get(2).getPlayerName());
    }

    @Test
    public void testSortScoresByScoreWorstCase() {
        // Create a list of scores with the same score but different times
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("Alice", 1000, 10000L));
        scores.add(new Score("Bob", 1000, 5000L));
        scores.add(new Score("Charlie", 1000, 7500L));
        scores.add(new Score("Dave", 1000, 2000L));
        scores.add(new Score("Eve", 1000, 8000L));

        // Sort the scores and check if they are sorted by time ascending
        List<Score> sortedScores = sorter.sortScoresByScore(scores);
        assertEquals("Dave", sortedScores.get(0).getPlayerName());
        assertEquals("Bob", sortedScores.get(1).getPlayerName());
        assertEquals("Charlie", sortedScores.get(2).getPlayerName());
        assertEquals("Eve", sortedScores.get(3).getPlayerName());
        assertEquals("Alice", sortedScores.get(4).getPlayerName());
    }
}
