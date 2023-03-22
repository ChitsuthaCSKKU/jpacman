package nl.tudelft.jpacman.data;

import nl.tudelft.jpacman.level.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Score {

    private String playerName;
    private int score;
    private long playingTime;

    public Score() {

    }

    public Score(Player player, long playingTime) {
        this.playerName = player.getName();
        this.score = player.getScore();
        this.playingTime = playingTime;
    }

    public Score(String playerName, int score, long playingTime) {
        this.playerName = playerName;
        this.score = score;
        this.playingTime = playingTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(long playingTime) {
        this.playingTime = playingTime;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("playerName", this.playerName);
        map.put("score", this.score);
        map.put("playingTime", this.playingTime);
        return map;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Score)) {
            return false;
        }
        Score other = (Score) obj;
        return Objects.equals(playerName, other.playerName)
            && score == other.score
            && playingTime == other.playingTime;
    }

}
