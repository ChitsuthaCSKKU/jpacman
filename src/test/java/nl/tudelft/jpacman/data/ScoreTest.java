package nl.tudelft.jpacman.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    @Test
    public void testToMap() {
        Score score = new Score("Alice", 100, 2000);
        assertEquals(score.toMap().get("playerName"), "Alice");
        assertEquals(score.toMap().get("score"), 100);
        assertEquals(score.toMap().get("playingTime"), 2000);
    }

    @Test
    public void testEquals() {
        Score score1 = new Score("Alice", 100, 2000);
        Score score2 = new Score("Bob", 100, 2000);
        Score score3 = new Score("Alice", 50, 2000);
        Score score4 = new Score("Alice", 100, 5000);

        assertFalse(score1.equals(null));
        assertFalse(score1.equals(new Object()));
        assertTrue(score1.equals(score1));
        assertFalse(score1.equals(score2));
        assertFalse(score1.equals(score3));
        assertFalse(score1.equals(score4));
        assertTrue(score1.equals(new Score("Alice", 100, 2000)));
    }
}
