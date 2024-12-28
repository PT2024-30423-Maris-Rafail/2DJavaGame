package Components;

import ConnecToDB.ConnectR;
import Display.*;
import GameState.State;
import GameState.TimeScore;
import geoGame.ScoreBoard;

import javax.swing.*;
import java.awt.*;

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

    private int getUserId() {
        System.out.println(gamePanel.isEmail);
        if (gamePanel.isEmail) return dbConnection.getUserIDEmail(gamePanel.username);
        return dbConnection.getUserIDUsername(gamePanel.username);
    }

    public void prepareEndingPanel() {
        //if login via email, we get the id via email, same for if by username
        userId = getUserId();

        gamePanel.state = State.GAME_END1;

        if (gamePanel.currentScore == null) gamePanel.currentScore = new TimeScore(gamePanel.timeInSec);
        int scoreId = dbConnection.addRun(gamePanel.currentScore, userId);

        handleScores();
        updateDB(scoreId);
        setUpUI();

        gamePanel.mainPanel.add(scorePanel, "Score");
    }

    private void handleScores() {
        gamePanel.bestTimeScore = dbConnection.getBestTime(userId);
        if (gamePanel.bestTimeScore == null) gamePanel.isFirstRound = true;
    }

    private void updateDB(int scoreId) {
        if (gamePanel.isFirstRound) {
            dbConnection.addBestRun(scoreId, userId);
        } else if (compareScores(gamePanel.bestTimeScore, gamePanel.currentScore) > 0) {
            dbConnection.updateBestRun(scoreId, userId);
        }
    }

    private void setUpUI() {
        if (scoreBoard == null) scoreBoard = dbConnection.getScoreBoard();
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
