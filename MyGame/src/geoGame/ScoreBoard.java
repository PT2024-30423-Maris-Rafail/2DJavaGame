package geoGame;

public class ScoreBoard {
    public ScoreBoardRow[] scoreBoard;

    public ScoreBoard() {
        scoreBoard = new ScoreBoardRow[10];
        for (int i = 0; i < scoreBoard.length; i++) {
            scoreBoard[i] = new ScoreBoardRow();
        }

    }
}
