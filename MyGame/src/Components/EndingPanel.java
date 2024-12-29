package Components;

import ConnecToDB.ConnectR;
import Display.*;
import GameState.State;
import GameState.TimeScore;
import geoGame.ScoreBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that appears after game has ended
 */
public class EndingPanel {
    ScoreBoard scoreBoard;
    ScoreTable tableModel;
    JTable showingTable;
    GamePanel gamePanel;
    JScrollPane scrollPane;
    Color highlightColor;
    CellColorer rowRenderer;
    ScorePanel scorePanel;
    int userId;
    ConnectR dbConnection;

    public EndingPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        highlightColor = new Color(182, 60, 60);
        dbConnection = gamePanel.dbConnection;
    }

    /**
     * Compares scores (hour, minute, second, millisecond based)
     */
    private int compareScores(TimeScore score1, TimeScore score2) {
        if (score1.hour > score2.hour) {
            return 1;
        }
        if (score1.hour < score2.hour) {
            return -1;
        }
        if (score1.minutes > score2.minutes) {
            return 1;
        }
        if (score1.minutes < score2.minutes) {
            return -1;
        }
        if (score1.seconds > score2.seconds) {
            return 1;
        }
        if (score1.seconds < score2.seconds) {
            return -1;
        }
        return Integer.compare(score1.milliseconds, score2.milliseconds);
    }

    /**
     * Accesses database to get the id
     */
    private int getUserId() {
        //System.out.println(gamePanel.isEmail);
        if (gamePanel.isEmail) return dbConnection.accountConnect.getUserIDEmail(gamePanel.username);
        return dbConnection.accountConnect.getUserIDUsername(gamePanel.username);
    }

    public void prepareEndingPanel() {
        //if login via email, we get the id via email, same for if by username
        userId = getUserId();

        gamePanel.state = State.GAME_END1;

        if (gamePanel.currentScore == null) gamePanel.currentScore = new TimeScore(gamePanel.timeInSec);
        int scoreId = dbConnection.accountConnect.addRun(gamePanel.currentScore, userId);

        handleScores();
        updateDB(scoreId);
        setUpUI();

        gamePanel.mainPanel.add(scorePanel, "Score");
    }

    /**
     * If the user hasn't played before, we set isFirstRound parameter
     */
    private void handleScores() {
        gamePanel.bestTimeScore = dbConnection.accountConnect.getBestTime(userId);
        if (gamePanel.bestTimeScore == null) gamePanel.isFirstRound = true;
    }

    /**
     * We update the best run id from users table
     * @param scoreId id of currently finished run
     */
    private void updateDB(int scoreId) {
        if (gamePanel.isFirstRound || compareScores(gamePanel.bestTimeScore, gamePanel.currentScore) > 0) {
            dbConnection.accountConnect.updateBestRun(scoreId, userId);
            dbConnection.accountConnect.cleanDB();
        }
    }

    /**
     * Shows the top 10 scores, and the current user's score is colored red
     * Doesn't show the admin's score
     */
    private void setUpUI() {
        if (scoreBoard == null) scoreBoard = dbConnection.accountConnect.getScoreBoard();
        if (tableModel == null) tableModel = new ScoreTable(scoreBoard);
        if (showingTable == null) showingTable = new JTable(tableModel);

        showingTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        showingTable.setRowHeight(30);

        if (scrollPane == null) scrollPane = new JScrollPane(showingTable);

        if (rowRenderer == null) rowRenderer = new CellColorer(gamePanel.username, highlightColor);
        for (int col = 0; col < showingTable.getColumnCount(); col++) {
            showingTable.getColumnModel().getColumn(col).setCellRenderer(rowRenderer);
        }
        if (scorePanel == null) scorePanel = new ScorePanel(scrollPane);
    }
}
