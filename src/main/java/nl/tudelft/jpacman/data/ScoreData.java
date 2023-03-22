package nl.tudelft.jpacman.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreData {
    private static String SCORE_FILE_PATH = "./data/offline-score.txt";

    public static List<Score> getListScore() {
        List<Score> scoreList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(SCORE_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String[] splitData = scanner.nextLine().split(" ");
                if (splitData.length != 3) {
                    continue;
                }
                try {
                    Score data = new Score(splitData[0], Integer.parseInt(splitData[1]), Long.parseLong(splitData[2]));
                    scoreList.add(data);
                } catch (NumberFormatException e) {
                    // Ignore invalid score entries
                }
            }
        } catch (FileNotFoundException e) {
            // Ignore if file does not exist
        }
        return scoreList;
    }

    public static boolean saveScore(Score score) {
        File fileScore = new File(SCORE_FILE_PATH);
        if (!fileScore.exists()) {
            try {
                fileScore.createNewFile();
            } catch (IOException error) {
                System.out.println("File can't create: " + error);
                return false;
            }
        }
        if (!fileScore.canWrite()) {
            System.out.println("File can't write.");
            return false;
        }
        try (FileWriter fw = new FileWriter(fileScore, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter writer = new PrintWriter(bw)) {
            writer.println(score.getPlayerName() + " " + score.getScore() + " " + score.getPlayingTime());
        } catch (IOException error) {
            System.out.println("File can't write: " + error);
            return false;
        }
        return true;
    }

    public static void setScoreFilePath(String scoreFilePath) {
        SCORE_FILE_PATH = scoreFilePath;
    }
}
