package nl.tudelft.jpacman.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreDataTest {

    private static final String TEST_SCORE_FILE_PATH = "./data/test-score.txt";

    @BeforeEach
    public void setUp() {
        ScoreData.setScoreFilePath(TEST_SCORE_FILE_PATH);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_SCORE_FILE_PATH));
    }

    @Test
    public void testSaveScore() {
        // Create a new score and save it to the test score file
        Score score = new Score("Alice", 1000, 10000);
        assertTrue(ScoreData.saveScore(score));

        // Read the test score file and verify that it contains the new score
        List<Score> scoreList = ScoreData.getListScore();
        assertEquals(1, scoreList.size());

        Score savedScore = scoreList.get(0);
        assertEquals("Alice", savedScore.getPlayerName());
        assertEquals(1000, savedScore.getScore());
        assertEquals(10000, savedScore.getPlayingTime());
    }

    @Test
    public void testGetListScore() throws IOException {
        // Prepare a test score file with three scores
        File testFile = new File(TEST_SCORE_FILE_PATH);
        testFile.createNewFile();
        Score score1 = new Score("Alice", 1000, 10000);
        Score score2 = new Score("Bob", 500, 5000);
        Score score3 = new Score("Carol", 750, 7500);
        assertTrue(ScoreData.saveScore(score1));
        assertTrue(ScoreData.saveScore(score2));
        assertTrue(ScoreData.saveScore(score3));

        // Retrieve the list of scores and verify that it contains the expected scores
        List<Score> scoreList = ScoreData.getListScore();
        assertEquals(3, scoreList.size());
        assertTrue(scoreList.contains(score1));
        assertTrue(scoreList.contains(score2));
        assertTrue(scoreList.contains(score3));
    }

}
